package com.example.repo_midterm.Exception;

import org.springframework.http.HttpStatus;
//Đặng Bá Hiền 22110320
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    SYSTEM_ERROR(5000, "System error: User data not found!", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(9998, "Invalid message key", HttpStatus.NOT_FOUND),
    // 1xxx: Authentication Errors (Lỗi xác thực)
    INVALID_USERNAME(1001, "Username must be a valid email address", HttpStatus.BAD_REQUEST),
    INVALID_OTP(1002, "Invalid OTP", HttpStatus.UNAUTHORIZED),
    ACCOUNT_NOT_FOUND(1003, "Account not found", HttpStatus.NOT_FOUND),
    USERNAME_FIELD(1005, "Username cannot be empty", HttpStatus.BAD_REQUEST),
    PASSWORD_FIELD(1006, "Password cannot be empty", HttpStatus.BAD_REQUEST),
    NAME_FIELD(1007, "Name cannot be empty", HttpStatus.BAD_REQUEST),
    GENDER_FIELD(1008, "Gender cannot be empty", HttpStatus.BAD_REQUEST),
    DOB_FIELD(1009, "Date of birth cannot be null", HttpStatus.BAD_REQUEST),


    // 2xxx: Validation Errors (Lỗi kiểm tra đầu vào)
    UNDERAGE_USER(2001, "You must be at least 18 years old to register", HttpStatus.BAD_REQUEST),
    USERNAME_ALREADY_EXISTS(2002, "Email is already registered", HttpStatus.CONFLICT),

    WEAK_PASSWORD_TOO_SHORT(2101, "Password must be at least 8 characters long", HttpStatus.BAD_REQUEST),
    WEAK_PASSWORD_NO_LOWERCASE(2102, "Password must contain at least one lowercase letter", HttpStatus.BAD_REQUEST),
    WEAK_PASSWORD_NO_UPPERCASE(2103, "Password must contain at least one uppercase letter", HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    private HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
