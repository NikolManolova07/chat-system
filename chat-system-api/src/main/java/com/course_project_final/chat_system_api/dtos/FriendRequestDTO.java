package com.course_project_final.chat_system_api.dtos;

import jakarta.validation.constraints.NotNull;

public class FriendRequestDTO {

    @NotNull(message = "User id cannot be null")
    private Integer userId;

    @NotNull(message = "Friend id cannot be null")
    private Integer friendId;

    public FriendRequestDTO(Integer userId, Integer friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public @NotNull(message = "User id cannot be null") Integer getUserId() {
        return userId;
    }

    public void setUserId(@NotNull(message = "User id cannot be null") Integer userId) {
        this.userId = userId;
    }

    public @NotNull(message = "Friend id cannot be null") Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(@NotNull(message = "Friend id cannot be null") Integer friendId) {
        this.friendId = friendId;
    }
}
