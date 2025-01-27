package com.course_project_final.chat_system_api.dtos;

import com.course_project_final.chat_system_api.entities.Channel;

public class ChannelResponseDTO {

    private Integer id;
    private String name;
    private UserResponseDTO owner;

    public ChannelResponseDTO(Channel channel) {
        this.id = channel.getId();
        this.name = channel.getName();
        this.owner = new UserResponseDTO(channel.getOwner());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserResponseDTO getOwner() {
        return owner;
    }

    public void setOwner(UserResponseDTO owner) {
        this.owner = owner;
    }
}
