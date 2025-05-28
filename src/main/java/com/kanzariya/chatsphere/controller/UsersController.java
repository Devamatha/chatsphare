package com.kanzariya.chatsphere.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.kanzariya.chatsphere.entity.Users;
import com.kanzariya.chatsphere.repository.UsersRepository;
import com.kanzariya.chatsphere.service.UsersService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/")

public class UsersController {

	private final UsersService usersService;
	private final UsersRepository usersRepository;

	@PostMapping("register")
	public ResponseEntity<Map<String, String>> register(@RequestBody Users user) {
		usersService.register(user);
		return ResponseEntity.ok(Collections.singletonMap("Message", "Data Saved Successfully"));
	}
}
