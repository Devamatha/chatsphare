package com.kanzariya.chatsphere.controller;

import org.springframework.web.bind.annotation.RestController;

import com.kanzariya.chatsphere.service.UsersService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UsersController {

	private final UsersService usersService;

}
