package com.course_project_final.chat_system_api.services;

import com.course_project_final.chat_system_api.entities.ChannelMember;
import com.course_project_final.chat_system_api.entities.Role;
import com.course_project_final.chat_system_api.exceptions.ResourceNotFoundException;
import com.course_project_final.chat_system_api.exceptions.UnauthorizedAccessException;
import com.course_project_final.chat_system_api.repositories.ChannelMemberRepository;
import com.course_project_final.chat_system_api.repositories.ChannelRepository;
import com.course_project_final.chat_system_api.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AccessControlService {

    private final ChannelRepository channelRepository;
    private final ChannelMemberRepository channelMemberRepository;
    private final UserRepository userRepository;

    public AccessControlService(ChannelRepository channelRepository, ChannelMemberRepository channelMemberRepository, UserRepository userRepository) {
        this.channelRepository = channelRepository;
        this.channelMemberRepository = channelMemberRepository;
        this.userRepository = userRepository;
    }

    public boolean checkUserRole(int channelId, int userId, Role role) {
        ChannelMember channelMember = channelMemberRepository.findByChannelIdAndUserIdAndIsDeletedFalse(channelId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("User is not a member of the channel"));

        return channelMember.getRole().equals(role);
    }

    @Transactional
    public String promoteAdminRole(int channelId, int userId, int ownerId) {
        channelRepository.findByIdAndIsDeletedFalse(channelId)
                .orElseThrow(() -> new ResourceNotFoundException("Channel not found with id: " + channelId));

        userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        userRepository.findByIdAndIsDeletedFalse(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + ownerId));

        if (!checkUserRole(channelId, ownerId, Role.OWNER)) {
            throw new UnauthorizedAccessException("Only the OWNER can assign roles");
        }

        ChannelMember targetMember = channelMemberRepository.findByChannelIdAndUserIdAndIsDeletedFalse(channelId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("User is not a member of the channel"));

        if (checkUserRole(channelId, userId, Role.OWNER)) {
            throw new UnauthorizedAccessException("OWNER cannot assign assign ADMIN role to themselves");
        }

        targetMember.setRole(Role.ADMIN);
        channelMemberRepository.save(targetMember);

        return "Admin role promoted successfully";
    }
}
