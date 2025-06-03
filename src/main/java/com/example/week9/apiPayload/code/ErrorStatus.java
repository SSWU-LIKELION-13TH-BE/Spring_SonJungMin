package com.example.week9.apiPayload.code;

import com.example.week9.apiPayload.dto.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."), TEMP_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4001", "테스트용 예외입니다."), USERNAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "EXISTSUSER400", "이미 존재하는 사용자입니다.");
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder().message(message).code(code).isSuccess(false).build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder().message(message).code(code).isSuccess(false).httpStatus(httpStatus).build();

    }
}