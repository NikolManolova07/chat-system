package com.course_project_final.chat_system_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ChannelMessageRequestDTO {

    @NotNull(message = "Sender id cannot be null")
    private Integer senderId;

    @NotNull(message = "Channel id cannot be null")
    private Integer channelId;

    @NotBlank(message = "Content cannot be blank")
    @Size(min = 1, message = "Content must have at least 1 character")
    private String content;

    public ChannelMessageRequestDTO(Integer senderId, Integer channelId, String content) {
        this.senderId = senderId;
        this.channelId = channelId;
        this.content = content;
    }

    public @NotNull(message = "Sender id cannot be null") Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(@NotNull(message = "Sender id cannot be null") Integer senderId) {
        this.senderId = senderId;
    }

    public @NotNull(message = "Channel id cannot be null") Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(@NotNull(message = "Channel id cannot be null") Integer channelId) {
        this.channelId = channelId;
    }

    public @NotBlank(message = "Content cannot be blank") String getContent() {
        return content;
    }

    public void setContent(@NotBlank(message = "Content cannot be blank") String content) {
        this.content = content;
    }
}
