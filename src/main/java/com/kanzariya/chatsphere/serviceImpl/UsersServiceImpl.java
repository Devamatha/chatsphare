package com.kanzariya.chatsphere.serviceImpl;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kanzariya.chatsphere.entity.Users;
import com.kanzariya.chatsphere.exceptions.UserNotFoundException;
import com.kanzariya.chatsphere.repository.UsersRepository;
import com.kanzariya.chatsphere.service.EmailService;
import com.kanzariya.chatsphere.service.UsersService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
	private final UsersRepository usersRepository;
	
	private final EmailService emailService;

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
	public void resetPassword(String email, String newPassword) {
	    Optional<Users> userOptional = usersRepository.findByEmail(email);

	    if (userOptional.isEmpty()) {
	        throw new UserNotFoundException("Invalid email address");
	    }

	    Users user = userOptional.get();
	    user.setPassword(bCryptPasswordEncoder.encode(newPassword));
	    usersRepository.save(user);

	    // Directly call sendEmail, no need for try-catch in this class
	    emailService.sendEmail(email, "Password Reset Confirmation", "Your password has been successfully reset.");
	}





}
