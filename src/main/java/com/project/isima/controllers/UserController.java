package com.project.isima.controllers;

import com.project.isima.dtos.UserDTO;
import com.project.isima.entities.User;
import com.project.isima.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }


    @PatchMapping ("/upadateUserInfo")
    public ResponseEntity<UserDTO> updateUserInfo(@RequestBody Map<String, Object> fields) {
        return ResponseEntity.ok(userService.updateUserInfo(fields));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
