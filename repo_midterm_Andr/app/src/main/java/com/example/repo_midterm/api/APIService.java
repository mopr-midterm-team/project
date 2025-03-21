package com.example.repo_midterm.api;

import com.example.repo_midterm.model.Category;
import com.example.repo_midterm.model.Phone;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//Nguyễn Văn Hoài - 22110327
public interface APIService {
    @GET("categories")
    Call<List<Category>> getCategoryAll();

    @GET("phonesH")
    Call<List<Phone>> getPhonesByCategory(@Query("categoryID") int categoryId, @Query("page") int page);

}
