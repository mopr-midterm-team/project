package com.example.repo_midterm.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//Đặng Bá Hiền 22110320
public class OTPResponse {
    private String username;
    private String otp;
    private String message;
}