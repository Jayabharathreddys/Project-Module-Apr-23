package com.scaler.blogapi.users;

import com.scaler.blogapi.security.TokenService;
import com.scaler.blogapi.users.dto.CreateUserRequestDTO;
import com.scaler.blogapi.users.dto.LoginUserRequestDTO;
import com.scaler.blogapi.users.dto.UserResponseDTO;
import com.scaler.blogapi.users.dto.UserUpdateDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;
    private final ModelMapper modelMapper;
    private final TokenService tokenService;

    public UsersController(
            @Autowired UsersService usersService,
            @Autowired ModelMapper modelMapper,
            @Autowired TokenService tokenService
    ) {
        this.usersService = usersService;
        this.modelMapper = modelMapper;
        this.tokenService = tokenService;
    }

    @PostMapping("/signup")
    ResponseEntity<UserResponseDTO> signupUser(@RequestBody CreateUserRequestDTO createUserRequestDTO) {

        var savedUser = usersService.createUser(
                createUserRequestDTO.getUsername(),
                createUserRequestDTO.getPassword(),
                createUserRequestDTO.getEmail()
        );
        var userResponse = modelMapper.map(savedUser, UserResponseDTO.class);
        userResponse.setToken(tokenService.createAuthToken(savedUser.getUsername()));
        return ResponseEntity.accepted().body(userResponse);
    }

    @PostMapping("/login")
    ResponseEntity<UserResponseDTO> loginUser(@RequestBody LoginUserRequestDTO loginUserRequestDTO) {
        var savedUser = usersService.loginUser(
                loginUserRequestDTO.getUsername(),
                loginUserRequestDTO.getPassword()
        );
        var userResponse = modelMapper.map(savedUser, UserResponseDTO.class);
        userResponse.setToken(tokenService.createAuthToken(savedUser.getUsername()));
        return ResponseEntity.accepted().body(userResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable UUID id,
                                                      @RequestBody UserUpdateDTO userUpdateDTO,
                                                      @RequestHeader("Authorization") String token) {
        // TODO 04:
        //  1. Create a UserUpdateDTO (containing email, password, bio)
        //     You can define the UserUpdateDTO class with the required fields.

        String userName = tokenService.getUsernameFromToken(token);

        //  3. Check that the client sends a token which validates this user
        if (userName == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        //  2. Call usersService.updateUser() with those details
        var updatedUser = usersService.updateUser(id, userUpdateDTO, token);

        //  4. Respond with 202 ACCEPTED if the user is updated successfully
        return ResponseEntity.accepted().body(updatedUser);
    }

}
