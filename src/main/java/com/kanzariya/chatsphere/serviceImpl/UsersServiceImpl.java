package com.kanzariya.chatsphere.serviceImpl;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kanzariya.chatsphere.entity.Users;
import com.kanzariya.chatsphere.exceptions.UserNotFoundException;
import com.kanzariya.chatsphere.repository.UsersRepository;
import com.kanzariya.chatsphere.service.EmailService;
import com.kanzariya.chatsphere.service.OTPService;
import com.kanzariya.chatsphere.service.UsersService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
	private final UsersRepository usersRepository;

	private final EmailService emailService;

	private final OTPService otpService;

	// PasswordEncoder bCryptPasswordEncoder;
	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	@Override
	public Users register(Users users) {

		users.setFullName(users.getFullName());
		users.setEmail(users.getEmail());
		users.setMobileNumber(users.getMobileNumber());
		if (!users.getPassword().isEmpty()) {
			users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));

		}
		users.setRole(users.getRole());
		return usersRepository.save(users);
	}

	@Override
	public void forgetPassword(String email) {
		Users userOptional = usersRepository.findByEmail(email);

		if (userOptional != null) {

			otpService.generateAndSendOTP(email);

		} else {
			throw new UserNotFoundException("User Not found " + email);
		}

	}

	
	  public List<Users> getUserByFullName(String fullName) {
	        return usersRepository.findByFullNameContainingIgnoreCase(fullName);
	    }
}
