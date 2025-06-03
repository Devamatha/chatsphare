package com.kanzariya.chatsphere.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.kanzariya.chatsphere.entity.Message;


public interface MessageService {
	 public Message sendMessage(Long senderId, Long receiverId, String content);
	 public List<Message> getMessages(Long receiverId);
	 public Message updateMessage(Long id,String content);
	 public ResponseEntity<Map<String,String>> deleteMessage(Long id);
}
