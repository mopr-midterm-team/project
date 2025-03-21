package com.example.repo_midterm.Controller;

import com.example.repo_midterm.DTO.Request.RegisterUserRequest;
import com.example.repo_midterm.DTO.Request.UserLoginRequest;
import com.example.repo_midterm.DTO.Request.VerifyOTPRepassRequest;
import com.example.repo_midterm.DTO.Request.VerifyOTPRequest;
import com.example.repo_midterm.DTO.Response.APIResponse;
import com.example.repo_midterm.DTO.Response.OTPResponse;
import com.example.repo_midterm.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // Cho phép mọi nguồn gửi yêu cầu
@Validated
public class UserController {

    @Autowired
    private UserService userService;
    //Đặng Bá Hiền 22110320
    @PostMapping("/register")
    public APIResponse<OTPResponse> register(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
        APIResponse<OTPResponse> apiResponse = new APIResponse<>();
        apiResponse.setResult(userService.registerUser(registerUserRequest));
        return apiResponse;
    }
    @PostMapping("/verify-email")
    public APIResponse<Void> verifyEmail(@Valid @RequestBody VerifyOTPRequest verifyOTPRequest) {
        APIResponse<Void> apiResponse = new APIResponse<>();
        userService.verifyOTP(verifyOTPRequest);
        return apiResponse;
    }
    //Đặng Bá Hiền 22110320
    @PostMapping("/forgot-pass")
    public APIResponse<OTPResponse> forgotPass(@Valid @RequestBody Map<String, String> request ) {
        APIResponse<OTPResponse> apiResponse = new APIResponse<>();
        apiResponse.setResult(userService.forgotPassword(request.get("username")));
        return apiResponse;
    }

    //Đặng Bá Hiền 22110320
    @PutMapping("/verify-email-repass")
    public APIResponse<Void> verifyEmailRepass(@Valid @RequestBody VerifyOTPRepassRequest verifyOTPRequest) {
        APIResponse<Void> apiResponse = new APIResponse<>();
        userService.verifyOTPRepass(verifyOTPRequest);
        return apiResponse;
    }

    //Võ Quang Nhật 22110390
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest userLoginRequest) {
        return userService.loginUser(userLoginRequest.getUsername(), userLoginRequest.getPassword());
    }
}
