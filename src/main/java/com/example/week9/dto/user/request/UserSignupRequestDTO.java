package com.example.week9.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

//회원가입 요청 DTO
@Data
public class UserSignupRequestDTO {
    @NotBlank(message = "아이디는 필수입니다.")
//    @Size(min = 2)
    private String userId;
    @NotBlank(message = "비밀번호는 필수입니다.")
//    @Size(min = 8)
    private String password;
    @NotBlank(message = "이름은 필수입니다.")
    private String name;
    private String profileImage;
}