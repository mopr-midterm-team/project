package com.example.repo_midterm.DTO;
//Đặng Bá Hiền 22110320
public class PasswordStrengthChecker {
    public static String checkPasswordStrength(String password) {
        if (password.length() < 8) {
            return "Mật khẩu phải có ít nhất 8 ký tự.";
        }

        if (!password.matches(".*[a-z].*")) {
            return "Mật khẩu phải chứa ít nhất 1 chữ thường.";
        }



        if (!password.matches(".*[A-Z].*")) {
            return "Mật khẩu phải chứa ít nhất 1 chữ hoa";
        }
        return null;
    }
}
