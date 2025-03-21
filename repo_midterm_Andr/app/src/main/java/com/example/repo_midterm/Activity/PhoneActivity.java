package com.example.repo_midterm.Activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.repo_midterm.R;
import com.example.repo_midterm.adapter.PhoneAdapter;
import com.example.repo_midterm.adapter.PhoneListAdapter;
import com.example.repo_midterm.api.PhoneAPI;
import com.example.repo_midterm.api.PhoneClientAPI;
import com.example.repo_midterm.model.Phone;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//Trần Anh Thư 22110431

public class PhoneActivity extends AppCompatActivity {

    RecyclerView rcPhone;
    PhoneListAdapter phoneListAdapter;
    PhoneAPI phoneAPI;
    List<Phone> phoneList;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        // Ánh xạ view và khởi tạo RecyclerView trước
        loadCategories();

        // Khởi động gọi API lấy dữ liệu
        GetCategory();
    }

    private void loadCategories() {
        rcPhone=(RecyclerView) findViewById(R.id.rc_category_1);
    }

    private void GetCategory() {
        phoneAPI = PhoneClientAPI.getRetrofit().create(PhoneAPI.class);
        phoneAPI.getLastPhones().enqueue(new Callback<List<Phone>>() {
            @Override
            public void onResponse(Call<List<Phone>> call, Response<List<Phone>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    phoneList = response.body();
                    phoneListAdapter = new PhoneListAdapter(PhoneActivity.this, phoneList);
                    Log.d("tag", "Dữ liệu nhận được thành công!");

                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(
                            PhoneActivity.this,
                            3 // Số cột là 3
                    );

                    rcPhone.setLayoutManager(layoutManager);
                    rcPhone.setAdapter(phoneListAdapter);

                } else {
                    int statusCode = response.code();
                    Log.e("API Error", "Lỗi: " + statusCode + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Phone>> call, Throwable t) {
                Log.e("API Error", "Không kết nối được: " + t.getMessage());
                t.printStackTrace(); // In chi tiết lỗi ra log
            }
        });
    }


}
