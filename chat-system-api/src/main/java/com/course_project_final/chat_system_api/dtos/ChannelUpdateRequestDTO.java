package com.course_project_final.chat_system_api.dtos;

import jakarta.validation.constraints.NotBlank;

public class ChannelUpdateRequestDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    public ChannelUpdateRequestDTO(String name) {
        this.name = name;
    }

    public @NotBlank(message = "Name cannot be blank") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name cannot be blank") String name) {
        this.name = name;
    }
}
