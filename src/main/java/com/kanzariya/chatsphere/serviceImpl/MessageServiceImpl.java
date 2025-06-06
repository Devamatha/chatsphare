package com.kanzariya.chatsphere.serviceImpl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kanzariya.chatsphere.entity.Message;
import com.kanzariya.chatsphere.entity.Users;
import com.kanzariya.chatsphere.exceptions.UserNotFoundException;
import com.kanzariya.chatsphere.repository.MessageRepository;
import com.kanzariya.chatsphere.repository.UsersRepository;
import com.kanzariya.chatsphere.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

	private MessageRepository messageRepository;
	private final UsersRepository usersRepository;

    public MessageServiceImpl(MessageRepository messageRepository, UsersRepository usersRepository) {
        this.messageRepository = messageRepository;
        this.usersRepository = usersRepository;
    }

    
    @Override
    public Message sendMessage(int senderId, int receiverId, Long groupId, String content) {
        // Validate sender ID exists
        Users sender = usersRepository.findById(senderId)
            .orElseThrow(() -> new UserNotFoundException("Sender not found with ID: " + senderId));

        // Validate receiver ID if it's a direct message
        Users receiver = null;  // Initialize for later use
         
            receiver = usersRepository.findById(receiverId)
                .orElseThrow(() -> new UserNotFoundException("Receiver not found with ID: " + receiverId));
        

        // Validate group ID if provided (optional feature for group validation)
//        if (groupId != null) {
//            boolean groupExists = groupRepository.existsById(groupId);
//            if (!groupExists) {
//                throw new GroupNotFoundException("Group not found with ID: " + groupId);
//            }
//        }

        // Create and store the message
        Message message = new Message();
        message.setSenderId(sender.getUser_Id()); // Convert int to Long
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());

        if (groupId != null) {
            message.setGroupId(groupId);  // Group chat message
        } else {
            message.setReceiverId(receiver != null ? receiver.getUser_Id() : null); // Convert int to Long
        }

        return messageRepository.save(message);
    }

    @Override
    public List<Message> getMessages(Long receiverId) {
        // Ensure receiver ID exists before fetching messages
        usersRepository.findById(receiverId.intValue())
            .orElseThrow(() -> new UserNotFoundException("Receiver not found with ID: " + receiverId));

        return messageRepository.findByReceiverId(receiverId);
    }

    @Override
    public List<Message> getMessagesByGroup(Long groupId) {
        return messageRepository.findByGroupId(groupId);
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
