package com.example.repo_midterm.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.repo_midterm.DTO.Request.VerifyOTPRequest;
import com.example.repo_midterm.DTO.Response.APIResponse;
import com.example.repo_midterm.R;
import com.example.repo_midterm.api.RetrofitClientOTP;
import com.example.repo_midterm.api.UserService;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//Đặng Bá Hiền 22110320
public class VerifyOTPActivity extends AppCompatActivity {

    private EditText otpET1, otpET2, otpET3, otpET4 , otpET5, otpET6;
    private Button verifyBtn;
    private TextView resendBtn,gmailTV;
    private boolean resendEnable = false;
    private int resendTime = 300;
    private int selecttedETPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verify_otp);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        String otp = getIntent().getStringExtra("otp");
        String username = getIntent().getStringExtra("username");
        String callType = getIntent().getStringExtra("CALL_TYPE");

        gmailTV = findViewById(R.id.gmailTV);
        gmailTV.setText(username);
        otpET1 = findViewById(R.id.otpET1);
        otpET2 = findViewById(R.id.otpET2);
        otpET3 = findViewById(R.id.otpET3);
        otpET4 = findViewById(R.id.otpET4);
        otpET5 = findViewById(R.id.otpET5);
        otpET6 = findViewById(R.id.otpET6);
        verifyBtn = findViewById(R.id.verifyBtn);
        resendBtn = findViewById(R.id.resendBtn);

        otpET1.addTextChangedListener(textWatcher);
        otpET2.addTextChangedListener(textWatcher);
        otpET3.addTextChangedListener(textWatcher);
        otpET4.addTextChangedListener(textWatcher);
        otpET5.addTextChangedListener(textWatcher);
        otpET6.addTextChangedListener(textWatcher);

        showKeyboard(otpET1);
        startCountDownTimer();
        resendBtn.setOnClickListener(v -> {
            if(resendEnable)
                startCountDownTimer();
        });

        verifyBtn.setOnClickListener(v ->{
            final String generateOTP = otpET1.getText().toString()+otpET2.getText().toString()+otpET3.getText().toString()+
                    otpET4.getText().toString()+otpET5.getText().toString()+otpET6.getText().toString();
            if(generateOTP.length()==6)
            {
                if(generateOTP.equals(otp))
                {
                    if ("fromRegister".equals(callType)) {
                        createAccount(new VerifyOTPRequest(otp,username));
                    } else if ("fromForgetPass".equals(callType)) {
                        forgetPassword(otp,username);
                    }
                }
                else
                {
                    Toast.makeText(this, "OTP không chính xác!", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(this, "Vui lòng nhập đủ 6 số!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void createAccount(VerifyOTPRequest verifyOTPRequest) {
        Retrofit retrofit = RetrofitClientOTP.getRetrofitInstance();
        UserService userService = retrofit.create(UserService.class);

        Call<APIResponse<Void>> call = userService.verifyEmail(verifyOTPRequest);
        call.enqueue(new Callback<APIResponse<Void>>() {
            @Override
            public void onResponse(Call<APIResponse<Void>> call, Response<APIResponse<Void>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    APIResponse<Void> apiResponse = response.body();
                    // Chuyển sang màn hình chính sau khi xác thực thành công
                    Intent intent = new Intent(VerifyOTPActivity.this, LoginActivity.class);
                    intent.putExtra("username", verifyOTPRequest.getUsername());
                    intent.putExtra("CALL_TYPE", "fromVerifyOTP");
                    startActivity(intent);
                    Toast.makeText(VerifyOTPActivity.this, "Xác thực thành công!", Toast.LENGTH_SHORT).show();
                    Log.d("API Response", "Xác thực OTP thành công.");
                } else {

                    Gson gson = new Gson();
                    APIResponse<?> errorResponse = gson.fromJson(response.errorBody().charStream(), APIResponse.class);
                    Toast.makeText(VerifyOTPActivity.this, "Đăng Ký tài thất bại: " + errorResponse.getMessage(), Toast.LENGTH_SHORT).show();                }
            }

            @Override
            public void onFailure(Call<APIResponse<Void>> call, Throwable t) {
                Toast.makeText(VerifyOTPActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API Error", "Error message: " + t.getMessage(), t);
            }
        });
    }


    private void forgetPassword(String otp, String username)
    {
        Intent intent = new Intent(VerifyOTPActivity.this, RepassActivity.class);
        intent.putExtra("otp", otp);
        intent.putExtra("username", username);
        startActivity(intent);
        Toast.makeText(VerifyOTPActivity.this, "Xác nhận OTP thành công!", Toast.LENGTH_SHORT).show();
    }

    private void startCountDownTimer(){
        resendEnable = false;
        resendBtn.setTextColor(Color.parseColor("#99000000"));

        new CountDownTimer(resendTime*1000,100){
            @Override
            public void onTick(long millisUntilFinished){
                int secondsRemaining = (int) millisUntilFinished / 1000;
                resendBtn.setText("Gửi lại mã sau (" + secondsRemaining + ")");
            }
            @Override
            public void onFinish(){
                resendEnable = true;
                resendBtn.setText("Gửi lại mã");
                resendBtn.setTextColor(getResources().getColor(R.color.primary));
            }
        }.start();
    }
    private void showKeyboard(EditText otpET)
    {
        otpET.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otpET, InputMethodManager.SHOW_IMPLICIT);
    }
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(editable.length()>0){
                switch (selecttedETPosition) {
                    case 4:
                        selecttedETPosition = 5;
                        showKeyboard(otpET6);
                        break;
                    case 3:
                        selecttedETPosition = 4;
                        showKeyboard(otpET5);
                        break;
                    case 2:
                        selecttedETPosition = 3;
                        showKeyboard(otpET4);
                        break;
                    case 1:
                        selecttedETPosition = 2;
                        showKeyboard(otpET3);
                        break;
                    case 0:
                        selecttedETPosition = 1;
                        showKeyboard(otpET2);
                        break;
                    default:
                        break;
                }
            }
        }
    };

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == event.KEYCODE_DEL){
            switch (selecttedETPosition) {
                case 5:
                    selecttedETPosition = 4;
                    showKeyboard(otpET5);

                    break;
                case 4:
                    selecttedETPosition = 3;
                    showKeyboard(otpET4);

                    break;
                case 3:
                    selecttedETPosition = 2;
                    showKeyboard(otpET3);

                    break;
                case 2:
                    selecttedETPosition = 1;
                    showKeyboard(otpET2);

                    break;
                case 1:
                    selecttedETPosition = 0;
                    showKeyboard(otpET1);
                    break;
                default:
                    break;
            }
            return true;

        }
        else
        {
            return super.onKeyUp(keyCode,event);
        }
    }
}