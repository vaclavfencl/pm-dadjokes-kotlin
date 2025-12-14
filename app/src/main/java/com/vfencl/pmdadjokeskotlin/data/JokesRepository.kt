package com.vfencl.pmdadjokeskotlin.data

interface JokesRepository {
    suspend fun getRandomJokeText(): String
}
