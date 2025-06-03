package com.kanzariya.chatsphere.service;

import java.util.List;

import com.kanzariya.chatsphere.entity.Message;


public interface MessageService {
	 public Message sendMessage(Long senderId, Long receiverId, String content);
	 public List<Message> getMessages(Long receiverId);
}
