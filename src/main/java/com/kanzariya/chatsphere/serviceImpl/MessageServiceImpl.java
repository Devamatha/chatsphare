package com.kanzariya.chatsphere.serviceImpl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kanzariya.chatsphere.entity.Message;
import com.kanzariya.chatsphere.exceptions.UserNotFoundException;
import com.kanzariya.chatsphere.repository.MessageRepository;
import com.kanzariya.chatsphere.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

	private MessageRepository messageRepository;

	public MessageServiceImpl(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	@Override
    public Message sendMessage(Long senderId, Long receiverId, Long groupId, String content) {
        Message message = new Message();
        message.setSenderId(senderId);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());
        
        if (groupId != null) {
            message.setGroupId(groupId);  
        } else {
            message.setReceiverId(receiverId);  
        }

        return messageRepository.save(message);
    }

    @Override
    public List<Message> getMessages(Long receiverId) {
        return messageRepository.findByReceiverId(receiverId);  // Fetch direct messages
    }

    @Override
    public List<Message> getMessagesByGroup(Long groupId) {
        return messageRepository.findByGroupId(groupId);  // Fetch group messages
    }


	@Override
	public Message updateMessage(Long id, String content) {
		Message optionalMessage = messageRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Message is Not found" + id));
		optionalMessage.setContent(content);
		return messageRepository.save(optionalMessage);
	}

	@Override
	public ResponseEntity<Map<String,String>> deleteMessage(Long id) {
		if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            return ResponseEntity.ok(Collections.singletonMap("Message", "Data Saved Successfully"));
        } else {
            throw new UserNotFoundException("Message ID "+id +"is not found");
        }
	}

}
