package com.example.repo_midterm.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.repo_midterm.DTO.Request.ForgotRequest;
import com.example.repo_midterm.DTO.Response.APIResponse;
import com.example.repo_midterm.DTO.Response.OTPResponse;
import com.example.repo_midterm.R;
import com.example.repo_midterm.api.RetrofitClientOTP;
import com.example.repo_midterm.api.UserService;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//Đặng Bá Hiền 22110320
public class ForgotPassActivity extends AppCompatActivity {
    private EditText usernameET;
    private Button getOTPBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_pass);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        usernameET = findViewById(R.id.usernameET);
        getOTPBtn = findViewById(R.id.getOTPBtn);
        getOTPBtn.setOnClickListener(v ->{
            String username = usernameET.getText().toString();
            getOTP(username);
        });
    }
    private void getOTP(String username)
    {
        Retrofit retrofit = RetrofitClientOTP.getRetrofitInstance();
        UserService userService = retrofit.create(UserService.class);
        Call<APIResponse<OTPResponse>> call = userService.forgetPass(new ForgotRequest(username)); // Gọi API với kiểu String (OTP)
        call.enqueue(new Callback<APIResponse<OTPResponse>>() {
            private EditText usernameET;
            private Button getOTPBtn;
            @Override
            public void onResponse(Call<APIResponse<OTPResponse>> call, Response<APIResponse<OTPResponse>> response) {
                if (response.isSuccessful()) {
                    APIResponse<OTPResponse> apiResponse = response.body();

                    Intent intent = new Intent(ForgotPassActivity.this, VerifyOTPActivity.class);

                    intent.putExtra("otp", apiResponse.getResult().getOtp());
                    intent.putExtra("username",username);
                    intent.putExtra("CALL_TYPE", "fromForgetPass");

                    startActivity(intent);
                    Log.d("API Response", "OTP: " + apiResponse.getResult().getOtp());  // OTP sẽ được hiển thị
                } else {
                    Gson gson = new Gson();
                    APIResponse<?> errorResponse = gson.fromJson(response.errorBody().charStream(), APIResponse.class);
                    Toast.makeText(ForgotPassActivity.this, "Đăng Ký tài thất bại: " + errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("API Error", "Error: " + errorResponse.getMessage());
                }
            }
            @Override
            public void onFailure(Call<APIResponse<OTPResponse>> call, Throwable t) {
                // Xử lý lỗi nếu có
                Toast.makeText(ForgotPassActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API Error", "Error message: " + t.getMessage(), t);
            }
        });
    }
}