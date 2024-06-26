package com.project.isima.controllers;

import com.project.isima.auth.ResponseMessage;
import com.project.isima.dtos.ReviewUser;
import com.project.isima.dtos.UserDTO;
import com.project.isima.dtos.UserForAdmin;
import com.project.isima.entities.Parcel;
import com.project.isima.entities.ResponsePicture;
import com.project.isima.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
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
    public ResponseEntity<UserDTO> getUserInfosById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserInfosById(id));
    }

    @GetMapping("/getMyInfos")
    public ResponseEntity<UserDTO> getMyInfos() {
        return ResponseEntity.ok(userService.getMyInfos());
    }

    @GetMapping("/getUserParcels/{id}")
    public ResponseEntity<List<Parcel>> getParcelsOfUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getParcelsOfUserById(id));
    }

    @GetMapping("/getUserReviews/{idDeliveryPerson}")
    public ResponseEntity<List<ReviewUser>> getReviewsOfUser(@PathVariable Long idDeliveryPerson) {
        return ResponseEntity.ok(userService.getReviewOfUserById(idDeliveryPerson));
    }

    @PatchMapping ("/updateUserInfo")
    public ResponseEntity<UserDTO> updateUserInfo(@RequestBody Map<String, Object> fields) {
        return ResponseEntity.ok(userService.updateUserInfo(fields));
    }

    @PatchMapping ("/updateUserPicture")
    public ResponseEntity<ResponsePicture> updateUserPicture(@RequestParam(value = "picture", required = false) MultipartFile picture) throws IOException {
        return ResponseEntity.ok(userService.updateUserPictureProfile(picture));
    }

    @PatchMapping ("/updateAccountStatus/{id}")
    public ResponseEntity<ResponseMessage> updateUserAccountStatus(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
        return ResponseEntity.ok(userService.updateUserAccountStatus(id, fields));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
