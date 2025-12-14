package com.vfencl.pmdadjokeskotlin.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://icanhazdadjoke.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val dadJokeApi: DadJokeApi by lazy {
        retrofit.create(DadJokeApi::class.java)
    }
}
