package com.kanzariya.chatsphere.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kanzariya.chatsphere.entity.Message;
import com.kanzariya.chatsphere.repository.MessageRepository;
import com.kanzariya.chatsphere.service.MessageService;
@Service
public class MessageServiceImpl implements MessageService {
	
	 private MessageRepository messageRepository;
	 public MessageServiceImpl(MessageRepository messageRepository) {
	        this.messageRepository = messageRepository;
	    }
	 public Message sendMessage(Long senderId, Long receiverId, String content) {
	        Message message = new Message();
	        message.setSenderId(senderId);
	        message.setReceiverId(receiverId);
	        message.setContent(content);
	        
	        return messageRepository.save(message);
	    }

	    public List<Message> getMessages(Long receiverId) {
	        return messageRepository.findByReceiverId(receiverId);
	    }
	
}
