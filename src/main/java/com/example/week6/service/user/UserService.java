package com.example.week6.service.user;

import com.example.week6.dto.user.request.UserLoginRequestDTO;
import com.example.week6.dto.user.request.UserSignupRequestDTO;
import com.example.week6.dto.user.response.UserLoginResponseDTO;
import com.example.week6.entity.user.User;
import com.example.week6.repository.user.UserRepository;
import com.example.week6.security.CustomUserDetails;
import com.example.week6.security.JwtTokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

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

//    public CustomUserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//
//
//    public void doSomething() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null && auth.isAuthenticated()) {
//            Object principal = auth.getPrincipal();
//
//            if (principal instanceof UserDetails) {
//                UserDetails userDetails = (UserDetails) principal;
//                String userId = userDetails.getUsername(); // 또는 userId
//                // 여기에 유저 정보 활용 로직 작성
//
//            }
//        }
//    }
}

