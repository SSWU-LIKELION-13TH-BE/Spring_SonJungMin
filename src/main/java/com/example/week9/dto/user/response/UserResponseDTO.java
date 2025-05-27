package com.example.week9.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class UserResponseDTO {
    private String userId;
    private String name;
    private String profileImage;

    public UserResponseDTO(Long id, String username, String email) {
        this.userId = userId;
        this.name = name;
        this.profileImage = profileImage;
    }
}
