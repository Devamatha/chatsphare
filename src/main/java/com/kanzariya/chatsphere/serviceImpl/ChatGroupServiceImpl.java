package com.kanzariya.chatsphere.serviceImpl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.kanzariya.chatsphere.entity.ChatGroup;
import com.kanzariya.chatsphere.entity.Users;
import com.kanzariya.chatsphere.repository.ChatGroupRepo;
import com.kanzariya.chatsphere.repository.UsersRepository;
import com.kanzariya.chatsphere.service.ChatGroupService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatGroupServiceImpl implements ChatGroupService {
	private final UsersRepository usersRepository;
	private final ChatGroupRepo chatGroupRepo;

	public ChatGroup createGroup(String groupName, int user_id) {
		Users creator = usersRepository.findById(user_id)
				.orElseThrow(() -> new RuntimeException("Creator not found"));
		ChatGroup group = new ChatGroup();
		group.setGroupName(groupName);
		group.setCreatedBy(creator);

		group.setMembers(new HashSet<>());
		return chatGroupRepo.save(group);
	}

	public ChatGroup addUsersToGroup(Long groupId, Set<Integer> userIds) {
		ChatGroup group = chatGroupRepo.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));

		Set<Users> newMembers = new HashSet<>(usersRepository.findAllById(userIds));
		group.getMembers().addAll(newMembers);
		return chatGroupRepo.save(group);
	}

	public ChatGroup removeUserFromGroup(Long groupId, Integer userId) {
		ChatGroup group = chatGroupRepo.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));

		group.getMembers().removeIf(user -> user.getUser_Id() == userId);
		return chatGroupRepo.save(group);
	}

	public Set<Users> getGroupMembers(Long groupId) {
		ChatGroup group = chatGroupRepo.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));
		return group.getMembers();
	}
}
