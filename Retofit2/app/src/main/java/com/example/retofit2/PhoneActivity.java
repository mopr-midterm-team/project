package com.example.retofit2;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.retofit2.adapter.PhoneAdapter;
import com.example.retofit2.api.PhoneAPI;
import com.example.retofit2.api.PhoneClientAPI;
import com.example.retofit2.model.Phone;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneActivity extends AppCompatActivity {

    RecyclerView rcPhone;
    PhoneAdapter phoneAdapter;
    PhoneAPI phoneAPI;
    List<Phone> phoneList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ view và khởi tạo RecyclerView trước
        loadCategories();

        // Khởi động gọi API lấy dữ liệu
        GetCategory();
    }

    private void loadCategories() {
        rcPhone=(RecyclerView) findViewById(R.id.rc_category);
    }

    private void GetCategory() {
        phoneAPI = PhoneClientAPI.getRetrofit().create(PhoneAPI.class);
        phoneAPI.getLastPhones().enqueue(new Callback<List<Phone>>() {
            @Override
            public void onResponse(Call<List<Phone>> call, Response<List<Phone>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    phoneList = response.body();
                    phoneAdapter = new PhoneAdapter(PhoneActivity.this, phoneList);
                    Log.d("tag", "Dữ liệu nhận được thành công!");

                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(
                            PhoneActivity.this,
                            3 // Số cột là 3
                    );

                    rcPhone.setHasFixedSize(true); // Tăng hiệu suất nếu không thay đổi kích thước
                    rcPhone.setLayoutManager(layoutManager);
                    rcPhone.setAdapter(phoneAdapter);

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

//    privatevoid GetCategory() {
//        phoneAPI = PhoneClientAPI.getRetrofit().create(PhoneAPI.class);
//        phoneAdapter = new PhoneAdapter(this, phoneList, new PhoneAdapter.OnPhoneClickListener() {
//            @Override
//            public void onPhoneClick(long phoneId) {
//                // Gửi request đến server để lấy dữ liệu của Phone với id đã chọn
//                PhoneAPI phoneAPI = PhoneClientAPI.getRetrofit().create(PhoneAPI.class);
//                Call<Phone> call = phoneAPI.getPhoneById(phoneId);
//
//                call.enqueue(new Callback<Phone>() {
//                    @Override
//                    public void onResponse(Call<Phone> call, Response<Phone> response) {
//                        if (response.isSuccessful() && response.body() != null) {
//                            Phone phone = response.body();
//                            Toast.makeText(PhoneActivity.this, "Nhận được: " + phone.getPhoneName(), Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(PhoneActivity.this, "Không tìm thấy dữ liệu!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Phone> call, Throwable t) {
//                        Toast.makeText(PhoneActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//
//    }


}
