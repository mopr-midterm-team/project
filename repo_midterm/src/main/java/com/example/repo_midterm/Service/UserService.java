package com.example.repo_midterm.Service;

import com.example.repo_midterm.DTO.Request.RegisterUserRequest;
import com.example.repo_midterm.DTO.Request.VerifyOTPRepassRequest;
import com.example.repo_midterm.DTO.Request.VerifyOTPRequest;
import com.example.repo_midterm.DTO.Response.OTPResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    OTPResponse registerUser(RegisterUserRequest registerUserRequest);
    void verifyOTP(VerifyOTPRequest verifyOTPRequest);
    OTPResponse forgotPassword(String username);
    void verifyOTPRepass(VerifyOTPRepassRequest verifyOTPRequest);
    //Võ Quang Nhật 22110390
    ResponseEntity<?> loginUser(String username, String password);
}
