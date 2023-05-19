package com.scaler.blogapi.users.dto;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NonNull;

@Data
public class UserUpdateDTO {

    @NonNull
    String email;
    @NonNull
    String password;
    @Nullable
    String bio;

}
