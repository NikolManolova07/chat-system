package com.course_project_final.chat_system_api.controllers;

import com.course_project_final.chat_system_api.dtos.*;
import com.course_project_final.chat_system_api.entities.Channel;
import com.course_project_final.chat_system_api.services.AccessControlService;
import com.course_project_final.chat_system_api.services.ChannelService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/channels")
public class ChannelController {

    private final ChannelService channelService;
    private final AccessControlService accessControlService;

    public ChannelController(ChannelService channelService, AccessControlService accessControlService) {
        this.channelService = channelService;
        this.accessControlService = accessControlService;
    }

    @GetMapping("/{channelId}")
    public ResponseEntity<ChannelResponseDTO> getChannelById(@PathVariable int channelId, @RequestParam int userId) {
        ChannelResponseDTO response = channelService.getChannelById(channelId, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{channelId}/members")
    public ResponseEntity<List<ChannelMemberResponseDTO>> getChannelMembers(@PathVariable int channelId) {
        List<ChannelMemberResponseDTO> response = channelService.getChannelMembers(channelId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Channel> createChannel(@RequestBody @Valid ChannelCreateRequestDTO channel) {
        Channel response = channelService.createChannel(channel);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{channelId}/members")
    public ResponseEntity<Map<String, String>> addMember(@PathVariable int channelId, @RequestParam int adminId, @RequestBody @Valid ChannelMemberRequestDTO channel) {
        String response = channelService.addMember(channelId, adminId, channel);
        Map<String, String> jsonResponse = new HashMap<>();
        jsonResponse.put("message", response);
        return ResponseEntity.ok(jsonResponse);
    }

    @PutMapping("/{channelId}")
    public ResponseEntity<ChannelResponseDTO> updateChannel(@PathVariable int channelId, @RequestParam int adminId, @RequestBody @Valid ChannelUpdateRequestDTO channel) {
        ChannelResponseDTO response = channelService.updateChannel(channelId, adminId, channel);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{channelId}/members/{userId}/promote")
    public ResponseEntity<Map<String, String>> promoteMemberToAdmin(@PathVariable int channelId, @PathVariable int userId, @RequestParam int ownerId) {
        String response = accessControlService.promoteAdminRole(channelId, userId, ownerId);
        Map<String, String> jsonResponse = new HashMap<>();
        jsonResponse.put("message", response);
        return ResponseEntity.ok(jsonResponse);
    }

    @DeleteMapping("/{channelId}")
    public ResponseEntity<Map<String, String>> deleteChannel(@PathVariable int channelId, @RequestParam int ownerId) {
        String response = channelService.deleteChannel(channelId, ownerId);
        Map<String, String> jsonResponse = new HashMap<>();
        jsonResponse.put("message", response);
        return ResponseEntity.ok(jsonResponse);
    }

    @DeleteMapping("/{channelId}/members/{userId}")
    public ResponseEntity<Map<String, String>> removeMember(@PathVariable int channelId, @PathVariable int userId, @RequestParam int ownerId) {
        String response = channelService.removeMember(channelId, userId, ownerId);
        Map<String, String> jsonResponse = new HashMap<>();
        jsonResponse.put("message", response);
        return ResponseEntity.ok(jsonResponse);
    }
}
