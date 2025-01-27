package com.course_project_final.chat_system_api.dtos;

import java.time.LocalDateTime;

public class DirectMessageResponseDTO {

    private UserResponseDTO sender;
    private UserResponseDTO receiver;
    private String content;
    private LocalDateTime createdAt;

    public DirectMessageResponseDTO(UserResponseDTO sender, UserResponseDTO receiver, String content, LocalDateTime createdAt) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.createdAt = createdAt;
    }

    public UserResponseDTO getSender() {
        return sender;
    }

    public void setSender(UserResponseDTO sender) {
        this.sender = sender;
    }

    public UserResponseDTO getReceiver() {
        return receiver;
    }

    public void setReceiver(UserResponseDTO receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
