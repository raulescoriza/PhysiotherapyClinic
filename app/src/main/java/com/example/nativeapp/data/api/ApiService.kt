package com.example.nativeapp.data.api

import com.example.nativeapp.data.model.PhotoTest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("posts/{id}")
    suspend fun getPosts(@Path("id") id: Int): PhotoTest

    @GET("api/character/{id}")
    suspend fun getPhoto(@Path("id") id: Int): PhotoTest

    @GET("api/character/")
    suspend fun getCharacters(): List<PhotoTest>

}