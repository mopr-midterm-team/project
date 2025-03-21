package com.example.repo_midterm.api;

import com.example.repo_midterm.DTO.Request.ForgotRequest;
import com.example.repo_midterm.DTO.Request.RegisterUserRequest;
import com.example.repo_midterm.DTO.Request.UserLoginRequest;
import com.example.repo_midterm.DTO.Request.VerifyOTPRepassRequest;
import com.example.repo_midterm.DTO.Request.VerifyOTPRequest;
import com.example.repo_midterm.DTO.Response.APIResponse;
import com.example.repo_midterm.DTO.Response.OTPResponse;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserService {
    @POST("/api/users/register")
    Call<APIResponse<OTPResponse>> register(@Body RegisterUserRequest registerUserRequest);
    @POST("/api/users/verify-email")
    Call<APIResponse<Void>> verifyEmail(@Body VerifyOTPRequest verifyOTPRequest);
    @POST("/api/users/forgot-pass")
    Call<APIResponse<OTPResponse>> forgetPass(@Body ForgotRequest username);
    @PUT("/api/users/verify-email-repass")
    Call<APIResponse<Void>> verifyEmailRepass(@Body VerifyOTPRepassRequest verifyOTPRepassRequest);
    @POST("api/users/login")
    Call<Map<String, Object>> loginUser(@Body UserLoginRequest userLoginDTO);
}
