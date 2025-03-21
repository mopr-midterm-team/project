package com.example.repo_midterm.api;


import com.example.repo_midterm.model.Phone;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
//Trần Anh Thư 22110431
public interface PhoneAPI {
    @GET("phones")
    Call<List<Phone>> getLastPhones();

    @GET("detail")
    Call<List<Phone>> getPhonesByPhoneDetail(@Query("phoneID") int phoneID);

    //Vuong Duc Thoai 22110430
    @GET("search")
    Call<List<Phone>> searchProducts(@Query("keyword") String keyword);


}
