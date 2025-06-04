package com.kanzariya.chatsphere.service;

import java.util.Set;

import com.kanzariya.chatsphere.entity.ChatGroup;
import com.kanzariya.chatsphere.entity.Users;

public interface ChatGroupService {
    public ChatGroup createGroup(String groupName,int user_id) ;
    public ChatGroup addUsersToGroup(Long groupId, Set<Integer> userIds);
    public ChatGroup removeUserFromGroup(Long groupId, Integer userId) ;
    public Set<Users> getGroupMembers(Long groupId) ;




}
