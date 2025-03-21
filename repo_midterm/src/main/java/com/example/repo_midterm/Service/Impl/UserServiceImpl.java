package com.example.repo_midterm.Service.Impl;

import com.example.repo_midterm.DTO.Request.RegisterUserRequest;
import com.example.repo_midterm.DTO.Request.VerifyOTPRepassRequest;
import com.example.repo_midterm.DTO.Request.VerifyOTPRequest;
import com.example.repo_midterm.DTO.Response.OTPResponse;
import com.example.repo_midterm.Entity.User;
import com.example.repo_midterm.Exception.AppException;
import com.example.repo_midterm.Exception.ErrorCode;
import com.example.repo_midterm.Mail.OTPGenerater;
import com.example.repo_midterm.Mail.UtilsEmail;
import com.example.repo_midterm.Repository.IUserRepository;
import com.example.repo_midterm.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class UserServiceImpl implements UserService {
    private static final ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
    public static final ConcurrentHashMap<String, User> otpStorage = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<String, String> otpRepass = new ConcurrentHashMap<>();
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    IUserRepository userRepository;
    @Autowired
    private UtilsEmail utilsEmail;

    // Đặng Bá Hiền 22110320
    @Override
    public OTPResponse registerUser(RegisterUserRequest registerUserRequest) {
        if (userRepository.findByUsername(registerUserRequest.getUsername()).isPresent()) {
            throw new AppException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }

        User user = new User();
        user.setUsername(registerUserRequest.getUsername());
        user.setPassword(registerUserRequest.getPassword());
        user.setDob(registerUserRequest.getDob());
        user.setGender(registerUserRequest.getGender());
        user.setName(registerUserRequest.getName());
        if(!validateUser(user.getDob()))
        {
            throw new AppException(ErrorCode.UNDERAGE_USER);
        }
        String OTP = OTPGenerater.generateUniqueOTP();

        emailExecutor.submit(() -> utilsEmail.sendEmail(user.getUsername(), "Xác nhận OTP", UtilsEmail.CreateContent(OTP)));
        otpStorage.put(OTP,user);
        return new OTPResponse(user.getUsername(), OTP, "OTP đã gửi, vui lòng xác nhận!");
    }
    // Đặng Bá Hiền 22110320
    @Override
    public void verifyOTP(VerifyOTPRequest verifyOTPRequest) {
        String otp = verifyOTPRequest.getOtp().replace("\"", "").trim();
        System.out.println(otp);
        String username = verifyOTPRequest.getUsername();
        User user = otpStorage.get(otp);
        if (user == null || !user.getUsername().equals(username)) {
            throw new AppException(ErrorCode.INVALID_OTP);
        }
        otpStorage.remove(otp);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    // Đặng Bá Hiền 22110320
    @Override
    public OTPResponse forgotPassword(String username) {
        if (!userRepository.existsByUsername(username)) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        String OTP = OTPGenerater.generateUniqueOTPRepass();
        emailExecutor.submit(() -> utilsEmail.sendEmail(username, "Xác nhận OTP", UtilsEmail.CreateContent(OTP)));
        otpRepass.put(OTP, username);
        return new OTPResponse(username, OTP, "OTP đã gửi, vui lòng xác nhận!");
    }

    // Đặng Bá Hiền 22110320
    @Override
    public void verifyOTPRepass(VerifyOTPRepassRequest verifyOTPRequest) {
        String otp = verifyOTPRequest.getOtp().replace("\"", "").trim();
        String username = otpRepass.get(otp);

        if (username == null || username.isEmpty() || !verifyOTPRequest.getUsername().equals(username)) {
            throw new AppException(ErrorCode.INVALID_OTP);
        }
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        otpRepass.remove(otp);
        user.setPassword(passwordEncoder.encode(verifyOTPRequest.getPassword()));
        userRepository.save(user);
    }

    // Đặng Bá Hiền 22110320
    public boolean validateUser(Date dob) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dob);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < calendar.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age>=18;
    }

    //Võ Quang Nhật 22110390
    public ResponseEntity<?> loginUser(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "Wrong username or password!"));
        }

        if (!passwordEncoder.matches(password, userOptional.get().getPassword())) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "Wrong username or password!"));
        }

        User user = userOptional.get();
        Map<String, Object> userData = new HashMap<>();
        userData.put("userID", user.getUserID());
        userData.put("username", user.getUsername());
        userData.put("password", user.getPassword());
        userData.put("name", user.getName());
        userData.put("gender", user.getGender());
        userData.put("dob", user.getDob());

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Login successful!",
                "user", userData
        ));
    }
}
