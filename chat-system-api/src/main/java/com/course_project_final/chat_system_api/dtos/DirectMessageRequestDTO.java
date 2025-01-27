package com.course_project_final.chat_system_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DirectMessageRequestDTO {

    @NotNull(message = "Sender id cannot be null")
    private Integer senderId;

    @NotNull(message = "Receiver id cannot be null")
    private Integer receiverId;

    @NotBlank(message = "Content cannot be blank")
    @Size(min = 1, message = "Content must have at least 1 character")
    private String content;

    public DirectMessageRequestDTO(Integer senderId, Integer receiverId, String content) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
    }

    public @NotNull(message = "Sender id cannot be null") Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(@NotNull(message = "Sender id cannot be null") Integer senderId) {
        this.senderId = senderId;
    }

    public @NotNull(message = "Receiver id cannot be null") Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(@NotNull(message = "Receiver id cannot be null") Integer receiverId) {
        this.receiverId = receiverId;
    }

    public @NotBlank(message = "Content cannot be blank") @Size(min = 1, message = "Content must have at least 1 character") String getContent() {
        return content;
    }

    public void setContent(@NotBlank(message = "Content cannot be blank") @Size(min = 1, message = "Content must have at least 1 character") String content) {
        this.content = content;
    }
}
