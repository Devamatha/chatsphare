package com.kanzariya.chatsphere.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kanzariya.chatsphere.entity.ChatGroup;

public interface ChatGroupRepo extends JpaRepository<ChatGroup, Long> {

}
