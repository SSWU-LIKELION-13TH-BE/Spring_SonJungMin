package com.example.week9.test.service;

import com.example.week9.apiPayload.code.ErrorStatus;
import com.example.week9.apiPayload.exception.GeneralException;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    public void checkFlag(Integer flag) {
        if (flag != null && flag == 1) {
            throw new GeneralException(ErrorStatus.TEMP_EXCEPTION);
        }
    }
}
