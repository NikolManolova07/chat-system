package com.course_project_final.chat_system_api.dtos;

import java.time.LocalDateTime;

public class FriendResponseDTO {

    private UserResponseDTO user;
    private UserResponseDTO friend;
    private LocalDateTime createdAt;

    public FriendResponseDTO(UserResponseDTO user, UserResponseDTO friend, LocalDateTime createdAt) {
        this.user = user;
        this.friend = friend;
        this.createdAt = createdAt;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }

    public UserResponseDTO getFriend() {
        return friend;
    }

    public void setFriend(UserResponseDTO friend) {
        this.friend = friend;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
