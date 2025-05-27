package com.example.week6.dto.user.request;

import lombok.Data;

@Data
public class UserPasswordRequestDTO {
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}