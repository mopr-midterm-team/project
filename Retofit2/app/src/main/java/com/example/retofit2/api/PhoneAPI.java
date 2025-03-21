package com.example.retofit2.api;

import com.example.retofit2.model.Category;
import com.example.retofit2.model.Phone;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PhoneAPI {
    @GET("phones")
    Call<List<Phone>> getLastPhones();

    @GET("detail")
    Call<List<Phone>> getPhonesByPhoneDetail(@Query("phoneID") int phoneID);

}
