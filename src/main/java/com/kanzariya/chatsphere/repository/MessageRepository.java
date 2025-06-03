package com.kanzariya.chatsphere.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kanzariya.chatsphere.entity.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByReceiverId(Long receiverId);
}

