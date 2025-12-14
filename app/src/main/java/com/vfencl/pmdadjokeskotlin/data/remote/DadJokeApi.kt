package com.vfencl.pmdadjokeskotlin.data.remote

import retrofit2.http.GET
import retrofit2.http.Headers

data class DadJokeDto(
    val id: String,
    val joke: String,
    val status: Int
)

interface DadJokeApi {
    @Headers(
        "Accept: application/json",
        "User-Agent: PMDadJokesKotlin (com.vfencl.pmdadjokeskotlin)"
    )
    @GET("/")
    suspend fun randomJoke(): DadJokeDto
}
