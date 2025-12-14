package com.vfencl.pmdadjokeskotlin.data

import com.vfencl.pmdadjokeskotlin.data.remote.DadJokeApi

class NetworkJokesRepository(
    private val api: DadJokeApi
) : JokesRepository {

    override suspend fun getRandomJokeText(): String {
        return api.randomJoke().joke
    }
}
