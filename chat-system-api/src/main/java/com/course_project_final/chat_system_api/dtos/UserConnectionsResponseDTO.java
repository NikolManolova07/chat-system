package com.course_project_final.chat_system_api.dtos;

import java.util.List;

public class UserConnectionsResponseDTO {

    private List<ChannelResponseDTO> channels;
    private List<UserResponseDTO> friends;

    public UserConnectionsResponseDTO(List<ChannelResponseDTO> channels, List<UserResponseDTO> friends) {
        this.channels = channels;
        this.friends = friends;
    }

    public List<ChannelResponseDTO> getChannels() {
        return channels;
    }

    public void setChannels(List<ChannelResponseDTO> channels) {
        this.channels = channels;
    }

    public List<UserResponseDTO> getFriends() {
        return friends;
    }

    public void setFriends(List<UserResponseDTO> friends) {
        this.friends = friends;
    }
}
