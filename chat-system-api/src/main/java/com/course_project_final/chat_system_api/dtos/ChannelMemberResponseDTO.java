package com.course_project_final.chat_system_api.dtos;

import com.course_project_final.chat_system_api.entities.Role;

import java.time.LocalDateTime;

public class ChannelMemberResponseDTO {

    private UserResponseDTO user;
    private Role role;
    private LocalDateTime joinedAt;

    public ChannelMemberResponseDTO(UserResponseDTO user, Role role, LocalDateTime joinedAt) {
        this.user = user;
        this.role = role;
        this.joinedAt = joinedAt;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }
}
