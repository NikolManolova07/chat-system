package com.course_project_final.chat_system_api.controllers;

import com.course_project_final.chat_system_api.dtos.ChannelMessageRequestDTO;
import com.course_project_final.chat_system_api.dtos.ChannelMessageResponseDTO;
import com.course_project_final.chat_system_api.dtos.DirectMessageRequestDTO;
import com.course_project_final.chat_system_api.dtos.DirectMessageResponseDTO;
import com.course_project_final.chat_system_api.services.MessageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/direct/{friendId}")
    public ResponseEntity<List<DirectMessageResponseDTO>> getDirectMessages(@PathVariable int friendId, @RequestParam int userId) {
        List<DirectMessageResponseDTO> response = messageService.getDirectMessages(friendId, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/channel/{channelId}")
    public ResponseEntity<List<ChannelMessageResponseDTO>> getChannelMessages(@PathVariable int channelId, @RequestParam int userId) {
        List<ChannelMessageResponseDTO> response = messageService.getChannelMessages(channelId, userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/direct")
    public ResponseEntity<Map<String, String>> sendDirectMessage(@RequestBody @Valid DirectMessageRequestDTO request) {
        String response = messageService.sendDirectMessage(request);
        Map<String, String> jsonResponse = new HashMap<>();
        jsonResponse.put("message", response);
        return ResponseEntity.ok(jsonResponse);
    }

    @PostMapping("/channel")
    public ResponseEntity<Map<String, String>> sendChannelMessage(@RequestBody @Valid ChannelMessageRequestDTO request) {
        String response = messageService.sendChannelMessage(request);
        Map<String, String> jsonResponse = new HashMap<>();
        jsonResponse.put("message", response);
        return ResponseEntity.ok(jsonResponse);
    }
}
