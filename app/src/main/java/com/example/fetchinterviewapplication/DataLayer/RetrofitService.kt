package com.example.fetchinterviewapplication.DataLayer


import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {
    @GET("hiring.json")
    fun getData(): Call<List<Model>>

    companion object {
        var BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"

        fun create(): RetrofitService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create()).baseUrl(
                    BASE_URL
                ).build()
            return retrofit.create(RetrofitService::class.java)
        }
    }
}