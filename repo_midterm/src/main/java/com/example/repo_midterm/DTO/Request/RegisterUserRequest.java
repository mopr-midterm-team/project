package com.example.repo_midterm.DTO.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
//Đặng Bá Hiền 22110320
public class RegisterUserRequest {
    @Email(message = "INVALID_USERNAME")
    @NotBlank(message = "USERNAME_FIELD")
    String username;
    @NotBlank(message = "PASSWORD_FIELD")
    @Pattern.List({
            @Pattern(regexp = "^.{8,}$", message = "WEAK_PASSWORD_TOO_SHORT"),
            @Pattern(regexp = ".*[a-z].*", message = "WEAK_PASSWORD_NO_LOWERCASE"),
            @Pattern(regexp = ".*[A-Z].*", message = "WEAK_PASSWORD_NO_UPPERCASE")
    })
    String password;
    @NotBlank(message = "NAME_FIELD")
    String name;
    @NotBlank(message = "GENDER_FIELD")
    String gender;
    @NotNull(message = "DOB_FIELD")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy hh:mm:ss a")
    Date dob;
}
