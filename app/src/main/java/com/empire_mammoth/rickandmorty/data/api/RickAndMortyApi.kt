package com.empire_mammoth.rickandmorty.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RickAndMortyApi {
    private const val BASE_URL = "https://rickandmortyapi.com/api/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val service: RickAndMortyApiService by lazy {
        retrofit.create(RickAndMortyApiService::class.java)
    }
}