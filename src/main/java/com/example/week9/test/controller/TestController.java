package com.example.week9.test.controller;

import com.example.week9.apiPayload.code.SuccessStatus;
import com.example.week9.apiPayload.dto.ApiResponse;
import com.example.week9.test.dto.SampleRequestDTO;
import com.example.week9.test.dto.TestResponse;
import com.example.week9.test.service.TestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;
    // 성공 응답 테스트
    @GetMapping("/hello")
    public ApiResponse<TestResponse> hello() {
        return ApiResponse.of(SuccessStatus._OK, new TestResponse("Hello, API!"));
    }
    // 예외 발생 테스트
    @GetMapping("/error")
    public ApiResponse<TestResponse> error(@RequestParam(required = false) Integer flag) {
        testService.checkFlag(flag); // flag==1일 때 예외 발생
        return ApiResponse.of(SuccessStatus._OK, new TestResponse("정상 처리되었습니다."));
    }
    // 유효성 검사 테스트
    @PostMapping("/validate")
    public ApiResponse<String> validate(@Valid @RequestBody SampleRequestDTO dto) {
        return ApiResponse.of(SuccessStatus._OK, "통과되었습니다");
    }
}


