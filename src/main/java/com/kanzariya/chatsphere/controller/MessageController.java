package com.kanzariya.chatsphere.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kanzariya.chatsphere.entity.Message;
import com.kanzariya.chatsphere.service.MessageService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestParam Long senderId, 
                                               @RequestParam Long receiverId, 
                                               @RequestParam String content) {
        Message message = messageService.sendMessage(senderId, receiverId, content);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{receiverId}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable Long receiverId) {
        List<Message> messages = messageService.getMessages(receiverId);
        return ResponseEntity.ok(messages);
    }
}