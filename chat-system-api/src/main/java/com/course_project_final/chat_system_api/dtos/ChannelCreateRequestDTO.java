package com.course_project_final.chat_system_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ChannelCreateRequestDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Owner id cannot be null")
    private Integer ownerId;

    public ChannelCreateRequestDTO(String name, Integer ownerId) {
        this.name = name;
        this.ownerId = ownerId;
    }

    public @NotBlank(message = "Name cannot be blank") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name cannot be blank") String name) {
        this.name = name;
    }

    public @NotNull(message = "Owner cannot be null") Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(@NotNull(message = "Owner cannot be null") Integer ownerId) {
        this.ownerId = ownerId;
    }
}
