package com.example.week9.apiPayload.code;

import com.example.week9.apiPayload.dto.ReasonDTO;

public interface BaseCode {
    ReasonDTO getReason(); // 메시지 및 코드 반환
    ReasonDTO getReasonHttpStatus(); // 메시지 + HTTP 상태 포함 반환
}
