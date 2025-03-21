package com.example.repo_midterm.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.repo_midterm.DTO.Request.UserLoginRequest;
import com.example.repo_midterm.R;
import com.example.repo_midterm.api.UserService;
import com.example.repo_midterm.api.RetrofitClientOTP;
import com.example.repo_midterm.model.User;
import com.google.gson.Gson;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//Võ Quang Nhật 22110390
public class LoginActivity extends AppCompatActivity {
    private UserService apiService;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        apiService = RetrofitClientOTP.getRetrofitInstance().create(UserService.class);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("username");
        editor.remove("password");
        editor.apply();

        TextView textViewForgetPassword = findViewById(R.id.textViewForgetPassword);
        TextView textViewRegister = findViewById(R.id.textViewRegister);
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        textViewForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPassActivity.class);
                startActivity(intent);
            }
        });
    }

    private void LoginUser() {
        username = editTextUsername.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        UserLoginRequest userLoginDTO = new UserLoginRequest(username, password);
        Call<Map<String, Object>> call = apiService.loginUser(userLoginDTO);
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
                            saveUserInfo(user);
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công! Xin chào " + user.getName(), Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivity.this, CategoriesActivity.class);
                            intent.putExtra("user", user);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Lỗi khi đọc dữ liệu người dùng!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Lỗi kết nối server!", Toast.LENGTH_SHORT).show();
                Log.e("LoginError", t.getMessage());
            }
        });
    }

    private void saveUserInfo(User user) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();
    }
}