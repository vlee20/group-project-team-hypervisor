package com.example.projexample.service

import com.example.projexample.model.YelpSearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

public interface SVCYelp {
    @GET("businesses/search")
    fun searchRestaurants(
        @Header("Authorization") authHeader: String,
        @Query("radius") radius: Int,
        @Query("longitude") longitude: Double,
        @Query("latitude") latitude: Double
    ) : Call<YelpSearchResult>
}