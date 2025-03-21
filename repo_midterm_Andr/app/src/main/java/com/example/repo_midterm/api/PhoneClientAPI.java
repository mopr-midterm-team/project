package com.example.repo_midterm.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


//Trần Anh Thư 22110431


public class PhoneClientAPI {
    private static Retrofit retrofit;
    public  static Retrofit getRetrofit(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080/api/")  // Đường dẫn API
                    .addConverterFactory(GsonConverterFactory.create()) // Chuyển đổi JSON về Object
                    .build();
        }
        return retrofit;
    }
    public static PhoneAPI getApi() {
        return getRetrofit().create(PhoneAPI.class);
    }
}
