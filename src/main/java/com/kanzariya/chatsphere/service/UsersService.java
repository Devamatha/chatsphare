package com.kanzariya.chatsphere.service;

import com.kanzariya.chatsphere.entity.Users;

public interface UsersService {
	//public Users login(String identifier, String password);

	public Users register(Users users);
	void resetPassword(String email, String newPassword);


}
