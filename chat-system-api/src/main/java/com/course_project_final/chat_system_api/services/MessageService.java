package com.course_project_final.chat_system_api.services;

import com.course_project_final.chat_system_api.dtos.*;
import com.course_project_final.chat_system_api.entities.Channel;
import com.course_project_final.chat_system_api.entities.Message;
import com.course_project_final.chat_system_api.entities.MessageType;
import com.course_project_final.chat_system_api.entities.User;
import com.course_project_final.chat_system_api.exceptions.ResourceNotFoundException;
import com.course_project_final.chat_system_api.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;
    private final ChannelRepository channelRepository;
    private final ChannelMemberRepository channelMemberRepository;


    public MessageService(MessageRepository messageRepository, UserRepository userRepository, FriendRepository friendRepository, ChannelRepository channelRepository, ChannelMemberRepository channelMemberRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.friendRepository = friendRepository;
        this.channelRepository = channelRepository;
        this.channelMemberRepository = channelMemberRepository;
    }

    public List<DirectMessageResponseDTO> getDirectMessages(int friendId, int userId) {
        userRepository.findByIdAndIsDeletedFalse(friendId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + friendId));

        userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        boolean existsFriendship = friendRepository.existsByUserIdAndFriendIdAndIsDeletedFalse(userId, friendId);

        if (!existsFriendship) {
            throw new IllegalStateException("Users cannot see or send any direct messages as they are not friends");
        }

        List<Message> messages = messageRepository.findDirectMessages(userId, friendId);

        return messages.stream()
                .map(msg -> new DirectMessageResponseDTO(
                        new UserResponseDTO(msg.getSender()),
                        new UserResponseDTO(msg.getReceiver()),
                        msg.getContent(),
                        msg.getCreatedAt()))
                .toList();
    }

    public List<ChannelMessageResponseDTO> getChannelMessages(int channelId, int userId) {
        channelRepository.findByIdAndIsDeletedFalse(channelId)
                .orElseThrow(() -> new ResourceNotFoundException("Channel not found with id: " + channelId));

        userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        boolean existsMembership = channelMemberRepository.existsByChannelIdAndUserIdAndIsDeletedFalse(channelId, userId);

        if (!existsMembership) {
            throw new IllegalStateException("User cannot see or send any channel messages as he/she is not a member of the channel");
        }

        List<Message> messages = messageRepository.findChannelMessages(channelId);

        return messages.stream()
                .map(msg -> new ChannelMessageResponseDTO(
                        new UserResponseDTO(msg.getSender()),
                        new ChannelResponseDTO(msg.getChannel()),
                        msg.getContent(),
                        msg.getCreatedAt()))
                .toList();
    }

    @Transactional
    public String sendDirectMessage(DirectMessageRequestDTO directMessageRequestDTO) {
        int senderId = directMessageRequestDTO.getSenderId();
        int receiverId = directMessageRequestDTO.getReceiverId();

        User sender = userRepository.findByIdAndIsDeletedFalse(senderId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + senderId));

        User receiver = userRepository.findByIdAndIsDeletedFalse(receiverId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + receiverId));

        boolean existsFriendship = friendRepository.existsByUserIdAndFriendIdAndIsDeletedFalse(senderId, receiverId);

        if (!existsFriendship) {
            throw new IllegalStateException("Users cannot send direct messages if they are not friends");
        }

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(directMessageRequestDTO.getContent());
        message.setMessageType(MessageType.DIRECT);

        messageRepository.save(message);

        return "Direct message sent successfully";
    }

    @Transactional
    public String sendChannelMessage(ChannelMessageRequestDTO channelMessageRequestDTO) {
        int senderId = channelMessageRequestDTO.getSenderId();
        int channelId = channelMessageRequestDTO.getChannelId();

        User sender = userRepository.findByIdAndIsDeletedFalse(senderId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + senderId));

        Channel channel = channelRepository.findByIdAndIsDeletedFalse(channelId)
                .orElseThrow(() -> new ResourceNotFoundException("Channel not found with id: " + channelId));

        boolean existsMembership = channelMemberRepository.existsByChannelIdAndUserIdAndIsDeletedFalse(channelId, senderId);

        if (!existsMembership) {
            throw new IllegalStateException("Users cannot send channel messages if they are not members of the channel");
        }

        Message message = new Message();
        message.setSender(sender);
        message.setChannel(channel);
        message.setContent(channelMessageRequestDTO.getContent());
        message.setMessageType(MessageType.CHANNEL);

        messageRepository.save(message);

        return "Channel message sent successfully";
    }
}
