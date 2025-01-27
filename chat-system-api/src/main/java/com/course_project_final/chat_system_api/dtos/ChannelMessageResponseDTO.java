package com.course_project_final.chat_system_api.dtos;

import java.time.LocalDateTime;

public class ChannelMessageResponseDTO {

    private UserResponseDTO sender;
    private ChannelResponseDTO channel;
    private String content;
    private LocalDateTime createdAt;

    public ChannelMessageResponseDTO(UserResponseDTO sender, ChannelResponseDTO channel, String content, LocalDateTime createdAt) {
        this.sender = sender;
        this.channel = channel;
        this.content = content;
        this.createdAt = createdAt;
    }

    public UserResponseDTO getSender() {
        return sender;
    }

    public void setSender(UserResponseDTO sender) {
        this.sender = sender;
    }

    public ChannelResponseDTO getChannel() {
        return channel;
    }

    public void setChannel(ChannelResponseDTO channel) {
        this.channel = channel;
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
