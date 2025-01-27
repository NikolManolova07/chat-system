package com.course_project_final.chat_system_api.dtos;

import jakarta.validation.constraints.NotNull;

public class ChannelMemberRequestDTO {

    @NotNull(message = "User id cannot be null")
    private Integer userId;

    public ChannelMemberRequestDTO(Integer userId) {
        this.userId = userId;
    }

    public @NotNull(message = "User id cannot be null") Integer getUserId() {
        return userId;
    }

    public void setUserId(@NotNull(message = "User id cannot be null") Integer userId) {
        this.userId = userId;
    }
}
