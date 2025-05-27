package com.kanzariya.chatsphere.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kanzariya.chatsphere.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {

}
