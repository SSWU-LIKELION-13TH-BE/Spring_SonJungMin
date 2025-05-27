package com.example.week6.service.user;

import com.example.week6.entity.user.User;
import com.example.week6.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordCheckService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

//    public boolean checkPassword(String rawPassword) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String username = auth.getName(); // 로그인된 사용자 이름
//
//        // 사용자 정보 가져오기
//        User user = userRepository.findByUserId(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        // 입력한 비밀번호와 실제 저장된 해시값 비교
//        return passwordEncoder.matches(rawPassword, user.getPassword());
//    }
    public boolean checkPassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }


}
