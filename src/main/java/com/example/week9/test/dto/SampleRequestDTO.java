package com.example.week9.test.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SampleRequestDTO {
    @NotBlank(message = "이름은 필수입니다.") // name 필드가 null이거나 빈 문자열이면 예외 발생
    private String name;
}
