package com.kanzariya.chatsphere.controller;

import java.util.Map;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kanzariya.chatsphere.entity.ChatGroup;
import com.kanzariya.chatsphere.entity.Users;
import com.kanzariya.chatsphere.service.ChatGroupService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class ChatGroupController {

	private final ChatGroupService chatGroupService;

	@PostMapping("/create/{user_id}")
	public ResponseEntity<ChatGroup> createGroup(@RequestParam String groupName,@PathVariable int user_id) {
		return ResponseEntity.ok(chatGroupService.createGroup(groupName,user_id));
	}

	@PostMapping("/{groupId}/add-users")
	public ResponseEntity<ChatGroup> addUsersToGroup(@PathVariable Long groupId, @RequestBody Set<Integer> userIds) {
		return ResponseEntity.ok(chatGroupService.addUsersToGroup(groupId, userIds));
	}

	@DeleteMapping("/{groupId}/remove-user/{userId}")
	public ResponseEntity<ChatGroup> removeUserFromGroup(@PathVariable Long groupId, @PathVariable Integer userId) {
		return ResponseEntity.ok(chatGroupService.removeUserFromGroup(groupId, userId));
	}

	@GetMapping("/{groupId}/members")
	public ResponseEntity<Set<Users>> getGroupMembers(@PathVariable Long groupId) {
		return ResponseEntity.ok(chatGroupService.getGroupMembers(groupId));
	}
}
