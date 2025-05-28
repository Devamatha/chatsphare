package com.kanzariya.chatsphere.serviceImpl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kanzariya.chatsphere.entity.Users;
import com.kanzariya.chatsphere.repository.UsersRepository;
import com.kanzariya.chatsphere.service.UsersService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
	private final UsersRepository usersRepository;

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

}
