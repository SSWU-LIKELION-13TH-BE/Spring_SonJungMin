package com.example.week6.dto.user.request;

import lombok.Data;

//로그인 요청 DTO
@Data
public class UserLoginRequestDTO {
    private String userId;
    private String password;
}
