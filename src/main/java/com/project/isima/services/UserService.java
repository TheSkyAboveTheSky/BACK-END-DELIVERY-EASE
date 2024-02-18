package com.project.isima.services;

import com.project.isima.auth.ResponseMessage;
import com.project.isima.dtos.UserDTO;
import com.project.isima.dtos.UserDTOById;
import com.project.isima.dtos.UserForAdmin;
import com.project.isima.entities.User;
import com.project.isima.enums.AccountStatus;
import com.project.isima.enums.Role;
import com.project.isima.exceptions.UserNotFoundException;
import com.project.isima.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserForAdmin> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserForAdmin> userDTOList = users.stream()
                .filter(user -> !user.getRole().equals(Role.ADMIN))
                .map(user -> new UserForAdmin(user.getId(),user.getFirstName(), user.getLastName(), user.getPhoneNumber(), user.getEmail(), user.getPicturePath(), user.getRole()))
                .toList();
        return userDTOList;
    }

    public UserDTOById getUserInfosById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found !"));
        return new UserDTOById(user.getFirstName(), user.getLastName());
    }

    public UserDTO getMyInfos() {
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByEmail(authenticatedUserEmail)
                .orElseThrow(() -> new UserNotFoundException("User Not Found !"));
        return new UserDTO(user.getFirstName(), user.getLastName(), user.getPhoneNumber(), user.getEmail());
    }

    public UserDTO updateUserInfo(Map<String, Object> fields) {
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<User> foundUser = userRepository.findUserByEmail(authenticatedUserEmail);

        if(foundUser.isPresent()) {
            User user = foundUser.get();
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(User.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, user, value);
            });
            userRepository.save(user);
            return new UserDTO(user.getFirstName(), user.getLastName(), user.getPhoneNumber(), user.getEmail());
        }
        return null;
    }

    public ResponseMessage updateUserAccountStatus(Long id, Map<String, Object> fields) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found !"));

        if(user != null) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(User.class, (String) key);
                field.setAccessible(true);
                // Vérifiez si le champ est une instance de Enum
                if (Enum.class.isAssignableFrom(field.getType())) {
                    // Convertissez la valeur de la chaîne en objet enum
                    Enum<?> enumValue = Enum.valueOf((Class<? extends Enum>) field.getType(), (String) value);
                    ReflectionUtils.setField(field, user, enumValue);
                } else {
                    // Si le champ n'est pas de type enum, définissez la valeur directement
                    ReflectionUtils.setField(field, user, value);
                }
            });
            userRepository.save(user);
            return new ResponseMessage("Un mise-à-jour a été effectuer a cet compte.");
        }
        return null;
    }
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found !"));
        userRepository.deleteById(id);
    }
}
