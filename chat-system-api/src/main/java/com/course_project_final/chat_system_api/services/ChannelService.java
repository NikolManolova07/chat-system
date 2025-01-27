package com.course_project_final.chat_system_api.services;

import com.course_project_final.chat_system_api.dtos.*;
import com.course_project_final.chat_system_api.entities.Channel;
import com.course_project_final.chat_system_api.entities.ChannelMember;
import com.course_project_final.chat_system_api.entities.Role;
import com.course_project_final.chat_system_api.entities.User;
import com.course_project_final.chat_system_api.exceptions.ResourceNotFoundException;
import com.course_project_final.chat_system_api.exceptions.UnauthorizedAccessException;
import com.course_project_final.chat_system_api.repositories.ChannelMemberRepository;
import com.course_project_final.chat_system_api.repositories.ChannelRepository;
import com.course_project_final.chat_system_api.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelService {

    private final ChannelRepository channelRepository;
    private final ChannelMemberRepository channelMemberRepository;
    private final UserRepository userRepository;
    private final AccessControlService accessControlService;

    public ChannelService(ChannelRepository channelRepository, ChannelMemberRepository channelMemberRepository, UserRepository userRepository, AccessControlService accessControlService) {
        this.channelRepository = channelRepository;
        this.channelMemberRepository = channelMemberRepository;
        this.userRepository = userRepository;
        this.accessControlService = accessControlService;
    }

    public ChannelResponseDTO getChannelById(int channelId, int userId) {
        userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Channel channel = channelRepository.findByIdAndIsDeletedFalse(channelId)
                .orElseThrow(() -> new ResourceNotFoundException("Channel not found with id: " + channelId));

        channelMemberRepository.findByChannelIdAndUserIdAndIsDeletedFalse(channelId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("User is not a member of the channel"));

        return new ChannelResponseDTO(channel);
    }

    public List<ChannelMemberResponseDTO> getChannelMembers(int channelId) {
        channelRepository.findByIdAndIsDeletedFalse(channelId)
                .orElseThrow(() -> new ResourceNotFoundException("Channel not found with id: " + channelId));

        List<ChannelMember> channelMembers = channelMemberRepository.findByChannelIdAndIsDeletedFalse(channelId);

        return channelMembers.stream()
                .map(cm -> new ChannelMemberResponseDTO(
                        new UserResponseDTO(cm.getUser()),
                        cm.getRole(),
                        cm.getJoinedAt()))
                .toList();
    }

    @Transactional
    public Channel createChannel(ChannelCreateRequestDTO createChannelDTO) {
        int ownerId = createChannelDTO.getOwnerId();

        User owner = userRepository.findByIdAndIsDeletedFalse(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + ownerId));

        Channel channel = new Channel();
        channel.setName(createChannelDTO.getName());
        channel.setOwner(owner);
        Channel savedChannel = channelRepository.save(channel);

        ChannelMember channelMember = new ChannelMember();
        channelMember.setChannel(savedChannel);
        channelMember.setUser(owner);
        channelMember.setRole(Role.OWNER);
        channelMemberRepository.save(channelMember);

        return savedChannel;
    }

    @Transactional
    public String addMember(int channelId, int adminId, ChannelMemberRequestDTO createChannelMemberDTO) {
        Channel channel = channelRepository.findByIdAndIsDeletedFalse(channelId)
                .orElseThrow(() -> new ResourceNotFoundException("Channel not found with id: " + channelId));

        userRepository.findByIdAndIsDeletedFalse(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + adminId));

        if (this.accessControlService.checkUserRole(channelId, adminId, Role.GUEST)) {
            throw new UnauthorizedAccessException("Only the OWNER and the ADMIN can add members to the channel");
        }

        int userId = createChannelMemberDTO.getUserId();
        User member = userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        ChannelMember channelMember = new ChannelMember();
        channelMember.setChannel(channel);
        channelMember.setUser(member);
        channelMember.setRole(Role.GUEST);
        channelMemberRepository.save(channelMember);

        return "Member added successfully to the channel";
    }

    @Transactional
    public ChannelResponseDTO updateChannel(int channelId, int ownerId, ChannelUpdateRequestDTO updateChannelDTO) {
        Channel channel = channelRepository.findByIdAndIsDeletedFalse(channelId)
                .orElseThrow(() -> new ResourceNotFoundException("Channel not found with id: " + channelId));

        userRepository.findByIdAndIsDeletedFalse(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + ownerId));

        if (this.accessControlService.checkUserRole(channelId, ownerId, Role.GUEST)) {
            throw new UnauthorizedAccessException("Only the OWNER and the ADMIN can update the name of the channel");
        }

        channel.setName(updateChannelDTO.getName());
        channelRepository.save(channel);

        return new ChannelResponseDTO(channel);
    }

    @Transactional
    public String deleteChannel(int channelId, int ownerId) {
        channelRepository.findByIdAndIsDeletedFalse(channelId)
                .orElseThrow(() -> new ResourceNotFoundException("Channel not found with id: " + channelId));

        userRepository.findByIdAndIsDeletedFalse(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + ownerId));

        if (!this.accessControlService.checkUserRole(channelId, ownerId, Role.OWNER)) {
            throw new UnauthorizedAccessException("Only the OWNER can delete the channel");
        }

        int rowsUpdated = channelRepository.softDeleteById(channelId);
        if (rowsUpdated == 0) {
            throw new IllegalStateException("Soft delete failed for channel with id: " + channelId);
        }

        return "Channel deleted successfully";
    }

    @Transactional
    public String removeMember(int channelId, int userId, int ownerId) {
        channelRepository.findByIdAndIsDeletedFalse(channelId)
                .orElseThrow(() -> new ResourceNotFoundException("Channel not found with id: " + channelId));

        userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        userRepository.findByIdAndIsDeletedFalse(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + ownerId));

        if (!this.accessControlService.checkUserRole(channelId, ownerId, Role.OWNER)) {
            throw new UnauthorizedAccessException("Only the OWNER can remove members from the channel");
        }

        if (!this.accessControlService.checkUserRole(channelId, userId, Role.GUEST)) {
            throw new UnauthorizedAccessException("The OWNER can remove only GUEST members from the channel");
        }

        int rowsUpdated = channelMemberRepository.softDeleteByChannelIdAndUserId(channelId, userId);
        if (rowsUpdated == 0) {
            throw new IllegalStateException("Soft delete failed for user with id: " + userId);
        }

        return "Member removed successfully from the channel";
    }
}
