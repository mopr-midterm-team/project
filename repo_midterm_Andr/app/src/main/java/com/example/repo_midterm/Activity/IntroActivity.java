package com.example.repo_midterm.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.repo_midterm.DTO.Request.UserLoginRequest;
import com.example.repo_midterm.api.RetrofitClientOTP;
import com.example.repo_midterm.api.UserService;
import com.example.repo_midterm.model.User;
import com.google.gson.Gson;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.repo_midterm.R;

//Võ Quang Nhật 22110390

public class IntroActivity extends AppCompatActivity {
    private UserService apiService;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_intro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button buttonStart = findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserInfo();
            }
        });

        apiService = RetrofitClientOTP.getRetrofitInstance().create(UserService.class);
    }

    private void getUserInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        username = sharedPreferences.getString("username", "");
        password = sharedPreferences.getString("password", "");

        if (TextUtils.isEmpty((username + password).trim()))
        {
            navigateToLoginActivity();
        }
        else
        {
            LoginUser();
        }
    }

    private void LoginUser() {
        UserLoginRequest loginRequest = new UserLoginRequest(username, password);
        Log.i("ABC", username + password);
        Call<Map<String, Object>> call = apiService.loginUser(loginRequest);
        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Map<String, Object> responseBody = response.body();
                    String status = (String) responseBody.get("status");

                    if ("success".equals(status)) {
                        Object userObj = responseBody.get("user");
                        if (userObj instanceof Map) {
                            Gson gson = new Gson();
                            String userJson = gson.toJson(userObj);
                            User user = gson.fromJson(userJson, User.class);
                            Toast.makeText(IntroActivity.this, "Đăng nhập thành công! Xin chào " + user.getName(), Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(IntroActivity.this, CategoriesActivity.class);
                            intent.putExtra("user", user);
                            startActivity(intent);
                        } else {
                            navigateToLoginActivity();
                            Toast.makeText(IntroActivity.this, "Lỗi khi đọc dữ liệu người dùng!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    navigateToLoginActivity();
                    Toast.makeText(IntroActivity.this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Toast.makeText(IntroActivity.this, "Lỗi kết nối server!", Toast.LENGTH_SHORT).show();
                Log.e("LoginError", t.getMessage());
            }
        });
    }

    private void navigateToLoginActivity()
    {
        Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}