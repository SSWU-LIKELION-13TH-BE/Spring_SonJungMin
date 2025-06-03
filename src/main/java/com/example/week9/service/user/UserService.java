package com.example.week9.service.user;

import com.example.week9.apiPayload.code.ErrorStatus;
import com.example.week9.apiPayload.exception.GeneralException;
import com.example.week9.dto.user.request.UserLoginRequestDTO;
import com.example.week9.dto.user.request.UserSignupRequestDTO;
import com.example.week9.dto.user.response.UserLoginResponseDTO;
import com.example.week9.entity.user.User;
import com.example.week9.repository.user.UserRepository;
import com.example.week9.security.CustomUserDetails;
import com.example.week9.security.JwtTokenProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // 🔑 회원가입 (비밀번호 암호화 후 저장)
    public void signup(UserSignupRequestDTO requestDto) {
        User user = new User();
        user.setUserId(requestDto.getUserId());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setName(requestDto.getName());
        user.setProfileImage(requestDto.getProfileImage());
        userRepository.save(user);
    }

    // 🔐 로그인 (ID/PW 검증 후 JWT 발급)
    public UserLoginResponseDTO login(UserLoginRequestDTO requestDto) {
        User user = userRepository.findByUserId(requestDto.getUserId()).orElseThrow(()
                -> new UsernameNotFoundException("User not found with ID: " + requestDto.getUserId()));
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        String token = jwtTokenProvider.createToken(user.getUserId());
        return new UserLoginResponseDTO(user.getUserId(), token);
    }

    @Transactional
    public void updatePassword(String userId, String newPassword) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // 사용자 정보 조회 (username 대신 email, userId 등도 가능)
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + userId));

        // UserDetails로 변환
        return new CustomUserDetails(user);
    }

    public void checkUser(String userId) {
        Optional<User> user = userRepository.findByUserId(userId);

        if (user.isPresent()) {
            throw new GeneralException(ErrorStatus.USERNAME_ALREADY_EXISTS);
        }
    }



}
