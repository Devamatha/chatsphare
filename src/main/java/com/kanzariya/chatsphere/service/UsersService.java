package com.kanzariya.chatsphere.service;

import java.util.List;

import com.kanzariya.chatsphere.entity.Users;

public interface UsersService {
	//public Users login(String identifier, String password);

	public Users register(Users users);
	//void forgetPassword(String email, String newPassword);
	void forgetPassword(String email);

	  public List<Users> getUserByFullName(String fullName) ;
	  
}
