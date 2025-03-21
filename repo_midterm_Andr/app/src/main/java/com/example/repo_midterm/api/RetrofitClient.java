package com.example.repo_midterm.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//Nguyễn Văn Hoài - 22110327
public class RetrofitClient {
    private static Retrofit retrofit;

    public static Retrofit getRetrofit(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static APIService getApi() {
        return getRetrofit().create(APIService.class);
    }
}
