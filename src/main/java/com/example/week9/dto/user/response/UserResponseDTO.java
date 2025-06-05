package com.example.week9.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class UserResponseDTO {
    private String userId;
    private String name;
    private String profileImage;
}
