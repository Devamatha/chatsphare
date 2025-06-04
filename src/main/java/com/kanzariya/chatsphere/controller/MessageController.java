package com.kanzariya.chatsphere.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kanzariya.chatsphere.entity.Message;
import com.kanzariya.chatsphere.service.MessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

	private final MessageService messageService;
	private final SimpMessagingTemplate simpMessagingTemplate;

//	@MessageMapping("/ws-chat/{receiverId}")
//	@DestinationVariable Long
	@PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestParam int senderId, 
                                               @RequestParam(required = false) int receiverId, 
                                               @RequestParam(required = false) Long groupId, 
                                               @RequestParam String content) {
        Message message = messageService.sendMessage(senderId, receiverId, groupId, content);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{receiverId}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable Long receiverId) {
        List<Message> messages = messageService.getMessages(receiverId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<Message>> getMessagesByGroup(@PathVariable Long groupId) {
        List<Message> messages = messageService.getMessagesByGroup(groupId);
        return ResponseEntity.ok(messages);
    }

	@PutMapping("/updateMessage/{id}")
	public Message updateMessage(@PathVariable Long id, @RequestParam String content) {
		return messageService.updateMessage(id, content);

	}

	@DeleteMapping("/deleteMessage/{id}")
	public ResponseEntity<Map<String, String>> deleteMessage(@PathVariable Long id) {
		return messageService.deleteMessage(id);
	}

}