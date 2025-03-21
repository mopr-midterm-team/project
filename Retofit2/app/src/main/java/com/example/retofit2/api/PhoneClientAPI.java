package com.example.retofit2.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhoneClientAPI {
    private static Retrofit retrofit;
    public  static Retrofit getRetrofit(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.100.56:8080/api/")  // Đường dẫn API
                    .addConverterFactory(GsonConverterFactory.create()) // Chuyển đổi JSON về Object
                    .build();
        }
        return retrofit;
    }
    public static PhoneAPI getApi() {
        return getRetrofit().create(PhoneAPI.class);
    }
}
