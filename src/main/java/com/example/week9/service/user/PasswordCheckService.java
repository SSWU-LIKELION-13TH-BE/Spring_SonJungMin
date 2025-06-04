package com.example.week9.service.user;

import com.example.week9.apiPayload.code.ErrorStatus;
import com.example.week9.apiPayload.exception.GeneralException;
import com.example.week9.entity.user.User;
import com.example.week9.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PasswordCheckService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    
    public void checkPassword(User user, String rawPassword) {
        boolean match = passwordEncoder.matches(rawPassword, user.getPassword());
        if (!match) {
            throw new GeneralException(ErrorStatus.PASSWORD_MISMATCH);
        }
    }

    public void checkNewPassword(String newPassword, String confirmPassword) {

        if (!newPassword.equals(confirmPassword)) {
            throw new GeneralException(ErrorStatus.PASSWORD_CONFIRM_MISMATCH);
        }
    }

}
