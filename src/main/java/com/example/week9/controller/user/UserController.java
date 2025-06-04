package com.example.week9.controller.user;

import com.example.week9.apiPayload.code.SuccessStatus;
import com.example.week9.apiPayload.dto.ApiResponse;
import com.example.week9.dto.user.request.UserLoginRequestDTO;
import com.example.week9.dto.user.request.UserPasswordRequestDTO;
import com.example.week9.dto.user.request.UserSignupRequestDTO;
import com.example.week9.dto.user.response.UserLoginResponseDTO;
import com.example.week9.dto.user.response.UserResponseDTO;
import com.example.week9.entity.user.User;
import com.example.week9.security.CustomUserDetails;
import com.example.week9.service.user.PasswordCheckService;
import com.example.week9.service.user.UserService;
import com.example.week9.test.dto.SampleRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final PasswordCheckService passwordCheckService;

    public UserController(UserService userService, PasswordCheckService passwordCheckService) {
        this.userService = userService;
        this.passwordCheckService = passwordCheckService;
    }

//     ✅ 회원가입
//    @PostMapping("/signup")
//    public ResponseEntity<String> signup(@RequestBody UserSignupRequestDTO requestDto){
//        userService.signup(requestDto);
//        return ResponseEntity.ok("회원가입 성공!");
//    }

    //회원가입 유효성 검사
    @PostMapping("/auth/signup")
    public ApiResponse<String> signup(@Valid @RequestBody UserSignupRequestDTO requestDto) {
        String userId = requestDto.getUserId();
        userService.checkUser(userId);
        userService.signup(requestDto);
        return ApiResponse.of(SuccessStatus._OK, "회원가입 되셨습니다.");
    }

    // ✅ 로그인
    @PostMapping("/users/login")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody UserLoginRequestDTO requestDto){
        UserLoginResponseDTO response = userService.login(requestDto);
        return ResponseEntity.ok(response);
    }

    /* 사용자 프로필 반환 API */
    /* @AuthenticationPrincipal 애노테이션 사용 -> userId GET */

    @GetMapping("/users/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        // 현재 인증된 사용자 정보 가져오기
        User user = userDetails.getUser();

        // 원하는 정보만 반환 (DTO로 감싸기)
        UserResponseDTO response = new UserResponseDTO(user.getUserId(), user.getName(), user.getProfileImage());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/user/password")
    public ApiResponse<String> getPassword(@AuthenticationPrincipal CustomUserDetails userDetails, @Valid @RequestBody UserPasswordRequestDTO requestDto ){
        User user = userDetails.getUser();

        passwordCheckService.checkPassword(user, requestDto.getCurrentPassword());
        passwordCheckService.checkNewPassword(requestDto.getNewPassword(), requestDto.getConfirmPassword());

        userService.updatePassword(user.getUserId(), requestDto.getNewPassword());
        return ApiResponse.of(SuccessStatus._OK, "비밀번호가 변경되었습니다.");
    }


}

