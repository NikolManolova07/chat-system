package com.course_project_final.chat_system_api.services;

import com.course_project_final.chat_system_api.dtos.*;
import com.course_project_final.chat_system_api.entities.Channel;
import com.course_project_final.chat_system_api.entities.Friend;
import com.course_project_final.chat_system_api.entities.User;
import com.course_project_final.chat_system_api.exceptions.ResourceNotFoundException;
import com.course_project_final.chat_system_api.repositories.ChannelMemberRepository;
import com.course_project_final.chat_system_api.repositories.FriendRepository;
import com.course_project_final.chat_system_api.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final FriendRepository friendRepository;
    private final ChannelMemberRepository channelMemberRepository;

    public UserService(UserRepository userRepository, FriendRepository friendRepository, ChannelMemberRepository channelMemberRepository) {
        this.userRepository = userRepository;
        this.friendRepository = friendRepository;
        this.channelMemberRepository = channelMemberRepository;
    }

    public UserResponseDTO getUserById(int userId) {
        User user = userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        return new UserResponseDTO(user);
    }

    public List<UserResponseDTO> searchUsers(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Keyword cannot be null or empty");
        }

        List<User> users = userRepository.searchUsersByKeyword(keyword);

        return users.stream()
                .map(UserResponseDTO::new)
                .toList();
    }

    public UserConnectionsResponseDTO getUserConnections(int userId) {
        userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        List<Channel> channels = channelMemberRepository.findChannelsByUserId(userId);
        List<User> friends = friendRepository.findFriendsByUserId(userId);

        List<ChannelResponseDTO> channelCollection = channels.stream()
                .map(ChannelResponseDTO::new)
                .toList();

        List<UserResponseDTO> friendCollection = friends.stream()
                .map(UserResponseDTO::new)
                .toList();

        return new UserConnectionsResponseDTO(channelCollection, friendCollection);
    }

    public FriendResponseDTO getFriendshipDetails(int userId, int friendId) {
        userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        userRepository.findByIdAndIsDeletedFalse(friendId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + friendId));

        return friendRepository.findFriendshipBetweenUsers(userId, friendId)
                .map(friend -> new FriendResponseDTO(
                        new UserResponseDTO(friend.getUser()),
                        new UserResponseDTO(friend.getFriend()),
                        friend.getCreatedAt()
                )).orElseThrow(() -> new IllegalStateException("Users are not friends"));
    }

    @Transactional
    public User createUser(UserRequestDTO createUserDTO) {
        User user = new User();

        user.setUsername(createUserDTO.getUsername());
        user.setPassword(createUserDTO.getPassword());
        user.setEmail(createUserDTO.getEmail());
        user.setFirstName(createUserDTO.getFirstName());
        user.setLastName(createUserDTO.getLastName());

        return userRepository.save(user);
    }

    @Transactional
    public String addFriend(FriendRequestDTO createFriendDTO) {
        int userId = createFriendDTO.getUserId();
        int friendId = createFriendDTO.getFriendId();

        User user = userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        User friend = userRepository.findByIdAndIsDeletedFalse(friendId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + friendId));

        if (user.equals(friend)) {
            throw new IllegalArgumentException("User cannot add themselves as a friend");
        }

        boolean existsForUser = friendRepository.existsByUserIdAndFriendIdAndIsDeletedFalse(userId, friendId);
        boolean existsForFriend = friendRepository.existsByUserIdAndFriendIdAndIsDeletedFalse(friendId, userId);

        if (existsForUser || existsForFriend) {
            throw new IllegalStateException("Users are already friends");
        }

        Friend friendshipForward = new Friend();
        friendshipForward.setUser(user);
        friendshipForward.setFriend(friend);

        Friend friendshipBackward = new Friend();
        friendshipBackward.setUser(friend);
        friendshipBackward.setFriend(user);

        friendRepository.save(friendshipForward);
        friendRepository.save(friendshipBackward);

        return "Successfully created friendship";
    }
}
