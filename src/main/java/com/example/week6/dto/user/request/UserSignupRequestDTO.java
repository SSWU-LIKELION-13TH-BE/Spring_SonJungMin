package com.example.week6.dto.user.request;

import lombok.Data;

//회원가입 요청 DTO
@Data
public class UserSignupRequestDTO {
    private String userId;
    private String password;
    private String name;
    private String profileImage;
}