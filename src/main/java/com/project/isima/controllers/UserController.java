package com.project.isima.controllers;

import com.project.isima.auth.ResponseMessage;
import com.project.isima.dtos.UserDTO;
import com.project.isima.dtos.UserDTOById;
import com.project.isima.dtos.UserForAdmin;
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

    @GetMapping("/all")
    public ResponseEntity<List<UserForAdmin>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/getUserInfos/{id}")
    public ResponseEntity<UserDTOById> getUserInfosById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserInfosById(id));
    }

    @GetMapping("/getMyInfos")
    public ResponseEntity<UserDTO> getMyInfos() {
        return ResponseEntity.ok(userService.getMyInfos());
    }

    @PatchMapping ("/upadateUserInfo")
    public ResponseEntity<UserDTO> updateUserInfo(@RequestBody Map<String, Object> fields) {
        return ResponseEntity.ok(userService.updateUserInfo(fields));
    }

    @PatchMapping ("/updateUser/{id}")
    public ResponseEntity<ResponseMessage> updateUserAccountStatus(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
        return ResponseEntity.ok(userService.updateUserAccountStatus(id, fields));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
