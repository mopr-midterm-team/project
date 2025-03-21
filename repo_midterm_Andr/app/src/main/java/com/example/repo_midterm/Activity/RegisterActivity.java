package com.example.repo_midterm.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.repo_midterm.DTO.PasswordStrengthChecker;
import com.example.repo_midterm.DTO.Request.RegisterUserRequest;
import com.example.repo_midterm.DTO.Response.APIResponse;
import com.example.repo_midterm.DTO.Response.OTPResponse;
import com.example.repo_midterm.R;
import com.example.repo_midterm.api.RetrofitClientOTP;
import com.example.repo_midterm.api.UserService;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//Đặng Bá Hiền 22110320
public class RegisterActivity extends AppCompatActivity {

    private TextView dateTV, signInBtn;
    private ImageView datePickerBtn;
    private Calendar calendar = Calendar.getInstance();
    private Button signUpBtn;
    private RadioGroup genderRadioGroup;

    private RadioButton maleRdb, femaleRdb, selectedRadioButton ;
    private EditText fullnameET, usernameET, passwordET;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fullnameET = findViewById(R.id.fullnameET);
        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        dateTV = findViewById(R.id.dateTV);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        maleRdb = findViewById(R.id.maleRdb);
        femaleRdb = findViewById(R.id.femaleRdb);
        datePickerBtn = findViewById(R.id.datePickerBtn);
        signInBtn = findViewById(R.id.signInBtn);
        signUpBtn = findViewById(R.id.signUpBtn);

        //Btn male luôn chọn trước để tránh xử lý th không rdb nào được chọn
        maleRdb.setChecked(true);
        //Set thoi gian hien tai
        String currentDate = dateFormat.format(calendar.getTime());
        dateTV.setText(currentDate);

        //Xu ly khi chon ngay
        datePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        signInBtn.setOnClickListener(v->{
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });

        signUpBtn.setOnClickListener(v->{
            //startActivity(new Intent(Register.this, OTPVerifycation.class));
            String fullname = fullnameET.getText().toString();
            String username = usernameET.getText().toString();
            String password = passwordET.getText().toString();
            int selectedId = genderRadioGroup.getCheckedRadioButtonId();
            String gender = ((RadioButton) findViewById(selectedId)).getText().toString();
            String dobString = dateTV.getText().toString();
            try {
                // Chuyển đổi chuỗi thành đối tượng Date
                Date dob = dateFormat.parse(dobString);
                signUp(fullname,username,password,gender,dob);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


    }
    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, monthOfYear, dayOfMonth);
                        String formattedDate = dateFormat.format(selectedDate.getTime());
                        dateTV.setText(formattedDate);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void signUp(String fullname, String username, String password, String gender, Date dob){

        // Kiểm tra nếu tên null
        if (fullname == null || fullname.isEmpty()) {
            fullnameET.requestFocus();
            Toast.makeText(this, "Tên không được để trống!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra nếu tên đăng nhập null
        if (username == null || username.isEmpty()) {
            usernameET.requestFocus();
            Toast.makeText(this, "Tên đăng nhập không được để trống!", Toast.LENGTH_SHORT).show();
            return;
        }

        String checkPW = PasswordStrengthChecker.checkPasswordStrength(password);
        // Kiểm tra nếu mật khẩu null
        if (checkPW != null) {
            passwordET.requestFocus();
            Toast.makeText(this, checkPW, Toast.LENGTH_SHORT).show();
            return;
        }

        RegisterUserRequest registerUserRequest = new RegisterUserRequest(username,password,fullname,gender,dob);
        Retrofit retrofit = RetrofitClientOTP.getRetrofitInstance();
        UserService userService = retrofit.create(UserService.class);
        Call<APIResponse<OTPResponse>> call = userService.register(registerUserRequest); // Gọi API với kiểu String (OTP)
        call.enqueue(new Callback<APIResponse<OTPResponse>>() {
            @Override
            public void onResponse(Call<APIResponse<OTPResponse>> call, Response<APIResponse<OTPResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    APIResponse<OTPResponse> apiResponse = response.body();

                    if (apiResponse.getCode() == 1000) {  // Kiểm tra mã code thành công
                        OTPResponse otpResponse = apiResponse.getResult();
                        String otp = otpResponse.getOtp();  // Lấy OTP từ kết quả

                        Intent intent = new Intent(RegisterActivity.this, VerifyOTPActivity.class);
                        intent.putExtra("otp", otp);
                        intent.putExtra("username", registerUserRequest.getUsername());
                        intent.putExtra("CALL_TYPE", "fromRegister");
                        startActivity(intent);

                        Log.d("API Response", "OTP: " + otp);
                    } else {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thất bại: " + apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Gson gson = new Gson();
                    APIResponse<?> errorResponse = gson.fromJson(response.errorBody().charStream(), APIResponse.class);
                }
            }

            @Override
            public void onFailure(Call<APIResponse<OTPResponse>> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API Error", "Error message: " + t.getMessage(), t);
            }
        });
    }
}