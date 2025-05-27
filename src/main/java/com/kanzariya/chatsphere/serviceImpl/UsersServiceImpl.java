package com.kanzariya.chatsphere.serviceImpl;

import org.springframework.stereotype.Service;

import com.kanzariya.chatsphere.repository.UsersRepository;
import com.kanzariya.chatsphere.service.UsersService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
	private final UsersRepository usersRepository;

}
