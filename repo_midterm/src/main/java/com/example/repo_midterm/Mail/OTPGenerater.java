package com.example.repo_midterm.Mail;

import java.util.Random;

import static com.example.repo_midterm.Service.Impl.UserServiceImpl.otpRepass;
import static com.example.repo_midterm.Service.Impl.UserServiceImpl.otpStorage;
//Đặng Bá Hiền 22110320
public class OTPGenerater {

    public static String generateUniqueOTP() {
        String otp;
        do {
            otp = OTPGenerater.generateOTP(6);
        } while (otpStorage.containsKey(otp));
        return otp;
    }

    public static String generateUniqueOTPRepass() {
        String otp;
        do {
            otp = OTPGenerater.generateOTP(6);
        } while (otpRepass.containsKey(otp));
        return otp;
    }
    public static String generateOTP(int length) {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < length; i++) {
            otp.append(random.nextInt(10));  // Tạo số ngẫu nhiên từ 0-9
        }

        return otp.toString();
    }

}
