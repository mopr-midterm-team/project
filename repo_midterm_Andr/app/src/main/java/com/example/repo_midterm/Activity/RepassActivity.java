package com.example.repo_midterm.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.repo_midterm.DTO.PasswordStrengthChecker;
import com.example.repo_midterm.DTO.Request.VerifyOTPRepassRequest;
import com.example.repo_midterm.DTO.Response.APIResponse;
import com.example.repo_midterm.R;
import com.example.repo_midterm.api.RetrofitClientOTP;
import com.example.repo_midterm.api.UserService;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RepassActivity extends AppCompatActivity {
    private TextView gmailTV;
    private EditText passwordET, cfpasswordET;
    private Button changePassBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_repass);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        gmailTV = findViewById(R.id.gmailTV);
        passwordET = findViewById(R.id.passwordET);
        cfpasswordET = findViewById(R.id.cfpasswordET);
        changePassBtn = findViewById(R.id.changePassBtn);
        String otp = getIntent().getStringExtra("otp");
        String username = getIntent().getStringExtra("username");

        gmailTV.setText(username);
        changePassBtn.setOnClickListener(v -> {
            String password = passwordET.getText().toString();
            String cfpassword = cfpasswordET.getText().toString();
            if(checkPassword(password,cfpassword))
            {
                changePassword(otp,password,username);
            }
        });
    }
    private boolean checkPassword(String password, String cfpassword){
        if(password.equals(cfpassword))
        {
            String checkPW = PasswordStrengthChecker.checkPasswordStrength(password);
            if(checkPW != null)
            {
                Toast.makeText(this, checkPW, Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }
        else
        {
            Toast.makeText(this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void changePassword(String otp, String password, String username)
    {
        Retrofit retrofit = RetrofitClientOTP.getRetrofitInstance();
        UserService userService = retrofit.create(UserService.class);
        Call<APIResponse<Void>> call = userService.verifyEmailRepass(new VerifyOTPRepassRequest(otp,username,password));
        call.enqueue(new Callback<APIResponse<Void>>() {
            @Override
            public void onResponse(Call<APIResponse<Void>> call, Response<APIResponse<Void>> response) {
                if (response.isSuccessful()) {
                    startActivity(new Intent(RepassActivity.this, LoginActivity.class));
                    Toast.makeText(RepassActivity.this, "Đặt lại mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Gson gson = new Gson();
                    APIResponse<?> errorResponse = gson.fromJson(response.errorBody().charStream(), APIResponse.class);
                    Toast.makeText(RepassActivity.this, "Đăng Ký tài thất bại: " + errorResponse.getMessage(), Toast.LENGTH_SHORT).show();                }
            }
            @Override
            public void onFailure(Call<APIResponse<Void>> call, Throwable t) {
                Toast.makeText(RepassActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API Error", "Error message: " + t.getMessage(), t);
            }
        });
    }
}