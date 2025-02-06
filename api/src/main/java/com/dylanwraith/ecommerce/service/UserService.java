package com.dylanwraith.ecommerce.service;

import com.dylanwraith.ecommerce.dto.UserDTO;
import com.dylanwraith.ecommerce.model.User;
import com.dylanwraith.ecommerce.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(UserDTO userDTO) {
        User createdUser = new User();
        createdUser.setFirstName(userDTO.firstName());
        createdUser.setLastName(userDTO.lastName());
        return userRepository.save(createdUser);
    }

    public User updateUser(Long id, UserDTO userDTO) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setFirstName(userDTO.firstName());
                    existingUser.setLastName(userDTO.lastName());
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
