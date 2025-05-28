package com.kanzariya.chatsphere.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import io.jsonwebtoken.security.Keys;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.env.Environment;
import io.jsonwebtoken.Jwts;

import com.kanzariya.chatsphere.constant.ApplicationConstants;
import com.kanzariya.chatsphere.entity.Users;
import com.kanzariya.chatsphere.records.LoginRequestDTO;
import com.kanzariya.chatsphere.records.LoginResponseDTO;
import com.kanzariya.chatsphere.repository.UsersRepository;
import com.kanzariya.chatsphere.service.UsersService;

import lombok.RequiredArgsConstructor;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/")

public class UsersController {

	private final UsersService usersService;
	private final UsersRepository usersRepository;
	private final AuthenticationManager authenticationManager;
	private final Environment env;

	@PostMapping("register")
	public ResponseEntity<Map<String, String>> register(@RequestBody Users user) {
		usersService.register(user);
		return ResponseEntity.ok(Collections.singletonMap("Message", "Data Saved Successfully"));
	}

	@PostMapping("/resetpassword")
	public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> requestBody) {
		String email = requestBody.get("email");
		String newPassword = requestBody.get("newPassword");

		if (email == null || newPassword == null) {
			return ResponseEntity.badRequest().body("Missing required parameters: email or newPassword.");
		}

		usersService.resetPassword(email, newPassword);
		return ResponseEntity.ok("Password reset successfully.");
	}

	@PostMapping("login")
	public ResponseEntity<LoginResponseDTO> apiLogin(@RequestBody LoginRequestDTO loginRequest) {
		String jwt = "";
		int id = 0;
		String fullName = "";
		String role = "";

		Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(),
				loginRequest.password());
		Authentication authenticationResponse = authenticationManager.authenticate(authentication);

		List<Object[]> result = usersRepository.findUser_IdAndFullNameByEmailOrMobile(authenticationResponse.getName());
		for (Object[] row : result) {
			id = (Integer) row[0];
			fullName = (String) row[1];
			// clientId = (Long) row[0];

		}

		role = authenticationResponse.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		if (null != authenticationResponse && authenticationResponse.isAuthenticated()) {
			if (null != env) {
				String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY,
						ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
				SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
				jwt = Jwts.builder().issuer("Chat_Sphere").subject("JWT Token")
						.claim("username", authenticationResponse.getName())
						.claim("authorities",
								authenticationResponse.getAuthorities().stream().map(GrantedAuthority::getAuthority)
										.collect(Collectors.joining(",")))
						.issuedAt(new java.util.Date())
						.expiration(new java.util.Date((new java.util.Date()).getTime() + 30000000)).signWith(secretKey)
						.compact();

			} else {
				System.out.println(env + "is not found");
			}

		} else {
			System.out.println(authenticationResponse + "is not found");
		}

		return ResponseEntity.status(HttpStatus.OK).header(ApplicationConstants.JWT_HEADER, jwt)
				.body(new LoginResponseDTO(HttpStatus.OK.getReasonPhrase(), jwt, id, fullName, role));
	}

}
