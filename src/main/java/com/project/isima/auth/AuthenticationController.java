package com.project.isima.auth;

import com.project.isima.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static com.project.isima.entities.ImageConstants.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/register")
    public ResponseEntity<ResponseMessage> register(
            @RequestParam(value = "picture", required = false) MultipartFile picture,
            @Valid @RequestBody RegisterRequest request
    ) throws IOException {
        if(picture == null || picture.isEmpty()) {
            request.setPicturePath(BASE_URL + "Anonyme.jpeg");
        } else {
            String fileName = picture.getOriginalFilename();
            // Create the images directory if it doesn't exist
            File imageDir = new File(ABSOLUTE_PATH + "\\" + DIRECTORY);
            if (!imageDir.exists()) {
                imageDir.mkdirs();
            }
            // Save the image file to the server
            File image = new File(imageDir, fileName);
            picture.transferTo(image);
            request.setPicturePath(BASE_URL + fileName);
        }
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ) throws IOException {
        AuthenticationResponse response = authenticationService.authenticate(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(AuthenticationResponse.builder()
                        .token(response.getToken())
                        .idUserAuthenticated(response.getIdUserAuthenticated())
                        .firstName(response.getFirstName())
                        .lastName(response.getLastName())
                        .phoneNumber(response.getPhoneNumber())
                        .role(response.getRole())
                        .picture(response.getPicture())
                        .message(response.getMessage())
                        .build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
