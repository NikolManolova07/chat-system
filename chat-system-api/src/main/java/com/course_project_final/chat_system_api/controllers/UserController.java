package com.course_project_final.chat_system_api.controllers;

import com.course_project_final.chat_system_api.dtos.*;
import com.course_project_final.chat_system_api.entities.User;
import com.course_project_final.chat_system_api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable int userId) {
        UserResponseDTO response = userService.getUserById(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserResponseDTO>> searchUsers(@RequestParam String keyword) {
        List<UserResponseDTO> response = userService.searchUsers(keyword);
        return ResponseEntity.ok((response));
    }

    @GetMapping("/{userId}/connections")
    public ResponseEntity<UserConnectionsResponseDTO> getUserConnections(@PathVariable int userId) {
        UserConnectionsResponseDTO response = userService.getUserConnections(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<FriendResponseDTO> getFriendshipDetails(@PathVariable int userId, @PathVariable int friendId) {
        FriendResponseDTO response = userService.getFriendshipDetails(userId, friendId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid UserRequestDTO user) {
        User response = userService.createUser(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/friends")
    public ResponseEntity<Map<String, String>> addFriend(@RequestBody @Valid FriendRequestDTO friend) {
        String response = userService.addFriend(friend);
        Map<String, String> jsonResponse = new HashMap<>();
        jsonResponse.put("message", response);
        return ResponseEntity.ok(jsonResponse);
    }
}
