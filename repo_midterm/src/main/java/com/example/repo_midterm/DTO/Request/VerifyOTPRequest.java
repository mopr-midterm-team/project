package com.example.repo_midterm.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//Đặng Bá Hiền 22110320
public class VerifyOTPRequest {
    private String otp;
    private String username;

}
