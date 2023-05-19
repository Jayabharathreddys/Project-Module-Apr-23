package com.scaler.blogapi.users;

import com.scaler.blogapi.users.dto.UserResponseDTO;
import com.scaler.blogapi.users.dto.UserUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;

    public UsersService(
            @Autowired UsersRepository usersRepository,
            @Autowired PasswordEncoder passwordEncoder
    ) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity createUser(String username, String password, String email) {
        var savedUser = usersRepository.save(
                UserEntity.builder()
                        .username(username)
                        .password(passwordEncoder.encode(password))
                        .email(email)
                        .build()
        );
        // TODO 02:
        //  1. create a UserEntity object with the given details
        //  2. save the user using usersRepository.save()
        //    a. validate username min/max lenght
        //    b. hash the password
        //    c. validate email format
        //  3. return the saved user
        return savedUser;
    }

    public UserEntity loginUser(String username, String password) {
        var savedUser = usersRepository.findByUsername(username);

        if (savedUser != null) {
            if (passwordEncoder.matches(password, savedUser.getPassword())) {
                return savedUser;
            }
        }
        throw new IllegalArgumentException("Invalid username or password");
        // TODO: have a proper UserNotFoundException class
        //return null;
    }

    public UserResponseDTO updateUser(UUID id, UserUpdateDTO userUpdateDTO, String token) {

        // Check if the provided token is valid for the user with the given id
        var existingUser = usersRepository.findById(id).orElse(null);
        if (existingUser == null || !isValidToken(existingUser, token)) {
            return null; // Invalid token or user not found
        }

        // Update the user details with the values from userUpdateDTO
        existingUser.setEmail(userUpdateDTO.getEmail());
        existingUser.setPassword( passwordEncoder.encode(userUpdateDTO.getPassword()) );
        existingUser.setBio(userUpdateDTO.getBio());

        // Save the updated user in the repository
        UserEntity updatedUser = usersRepository.save(existingUser);

        // Create and return the response DTO
        UserResponseDTO responseDTO = new UserResponseDTO(
                updatedUser.getEmail(),
                updatedUser.getBio());
        return responseDTO;
    }

    private boolean isValidToken(UserEntity user, String token) {
        // Perform token validation logic here
        // You can check if the token matches the user's stored token or perform any other validation
        // Return true if the token is valid, false otherwise
        return true;
    }
}
