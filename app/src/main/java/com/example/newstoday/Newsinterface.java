package com.example.newstoday;

import com.example.newstoday.ModelClasses.MainResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Newsinterface {

    @GET("/v2/top-headlines?country=in&apiKey=9987d0ce18624e38b1f027139f4f318b")
    Call<MainResponse> getNewsData(@Query("category") String category);
}
