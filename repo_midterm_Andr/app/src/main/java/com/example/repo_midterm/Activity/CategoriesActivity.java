package com.example.repo_midterm.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.repo_midterm.R;
import com.example.repo_midterm.adapter.CategoryAdapter;
import com.example.repo_midterm.adapter.PhoneListAdapter;
import com.example.repo_midterm.api.APIService;
import com.example.repo_midterm.api.RetrofitClient;
import com.example.repo_midterm.api.PhoneAPI;
import com.example.repo_midterm.api.PhoneClientAPI;
import com.example.repo_midterm.model.Category;
import com.example.repo_midterm.model.Phone;
import com.example.repo_midterm.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesActivity extends AppCompatActivity {
    private RecyclerView rcCategory, rcPhone;
    private CategoryAdapter categoryAdapter;
    private PhoneListAdapter phoneListAdapter;
    private APIService apiService;
    private PhoneAPI phoneAPI;
    private List<Category> categoryList;
    private List<Phone> phoneList;

    EditText searchBar;
//Trần Anh Thư 22110431

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        // Ánh xạ RecyclerView
        rcCategory = findViewById(R.id.rc_category);
        rcPhone = findViewById(R.id.rc_category_1);
        searchBar = findViewById(R.id.search_bar);

        // Gọi API để lấy dữ liệu
        fetchCategories();
        fetchPhones();


      //  Vuong Duc Thoai 22110430
        setupSearch();



        setupSearch();

        User user = (User) getIntent().getSerializableExtra("user");
        TextView textViewUser = findViewById(R.id.textViewUser);
        textViewUser.setText("Xin chào bạn, " + user.getName() + "!");

        //Võ Quang Nhật 22110390
        Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogoutUser();
            }
        });
    }

    //Võ Quang Nhật 22110390
    private void LogoutUser()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("username");
        editor.remove("password");
        editor.apply();

        Intent intent = new Intent(CategoriesActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(CategoriesActivity.this, "Đăng xuất thành công!", Toast.LENGTH_SHORT).show();
    }


    //Nguyễn Văn Hoài - 22110327
    private void fetchCategories() {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getCategoryAll().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categoryList = response.body();

                    if (categoryList.isEmpty()) {
                        Log.d("Logg", "Danh sách danh mục rỗng.");
                        return;
                    }

                    setupCategoryRecyclerView();
                } else {
                    Log.d("Logg", "Lỗi phản hồi từ server: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e("Logg", "Lỗi kết nối API: " + t.getMessage());
            }
        });
    }
//Trần Anh Thư 22110431

    private void fetchPhones() {
        phoneAPI = PhoneClientAPI.getRetrofit().create(PhoneAPI.class);
        phoneAPI.getLastPhones().enqueue(new Callback<List<Phone>>() {
            @Override
            public void onResponse(Call<List<Phone>> call, Response<List<Phone>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    phoneList = response.body();

                    if (phoneList.isEmpty()) {
                        Log.d("Logg", "Danh sách điện thoại rỗng.");
                        return;
                    }

                    setupPhoneRecyclerView();
                } else {
                    Log.d("Logg", "Lỗi phản hồi từ server: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Phone>> call, Throwable t) {
                Log.e("Logg", "Lỗi kết nối API: " + t.getMessage());
            }
        });
    }

    //Nguyễn Văn Hoài - 22110327
    private void setupCategoryRecyclerView() {
        categoryAdapter = new CategoryAdapter(this, categoryList);
        rcCategory.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
        );

        rcCategory.setLayoutManager(layoutManager);
        rcCategory.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
        Log.d("Logg", "Adapter danh mục đã được gán vào RecyclerView");
    }

    private void setupPhoneRecyclerView() {
        phoneListAdapter = new PhoneListAdapter(this, phoneList);
        rcPhone.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        rcPhone.setLayoutManager(layoutManager);
        rcPhone.setAdapter(phoneListAdapter);
        phoneListAdapter.notifyDataSetChanged();
        Log.d("Logg", "Adapter điện thoại đã được gán vào RecyclerView");
    }

    private void setupSearch() {
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim();
                if (!query.isEmpty()) {
                    searchProducts(query);
                } else {
                    updateRecyclerView(phoneList);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void searchProducts(String keyword) {
        phoneAPI.searchProducts(keyword).enqueue(new Callback<List<Phone>>() {
            @Override
            public void onResponse(Call<List<Phone>> call, Response<List<Phone>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Phone> searchResults = response.body();
                    Log.d("SearchAPI", "Found " + searchResults.size() + " results for: " + keyword);
                    for (Phone product : searchResults) {
                        Log.d("SearchAPI", "Product: " + product.getPhoneName());
                    }
                    updateRecyclerView(searchResults);
                } else {
                    Log.e("SearchAPI", "Response failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Phone>> call, Throwable t) {
                Log.e("SearchAPI", "API call failed: " + t.getMessage());
            }
        });
    }

    private void updateRecyclerView(List<Phone> list) {
        if (list == null || list.isEmpty()) {
            Log.d("SearchAPI", "No products found.");
        } else {
            Log.d("SearchAPI", "Updating RecyclerView with " + list.size() + " products.");
            for (Phone product : list) {
                Log.d("SearchAPI", "Product: " + product.getPhoneName());
            }
        }
        phoneListAdapter.updateList(list);
    }

}
