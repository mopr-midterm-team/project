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
public class VerifyOTPRepassRequest {
    private String otp;
    private String username;
    @NotBlank(message = "PASSWORD_FIELD")
    @Pattern.List({
            @Pattern(regexp = "^.{8,}$", message = "WEAK_PASSWORD_TOO_SHORT"),
            @Pattern(regexp = ".*[a-z].*", message = "WEAK_PASSWORD_NO_LOWERCASE"),
            @Pattern(regexp = ".*[A-Z].*", message = "WEAK_PASSWORD_NO_UPPERCASE")
    })
    private String password;
}
