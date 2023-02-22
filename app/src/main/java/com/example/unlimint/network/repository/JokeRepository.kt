package com.example.unlimint.network.repository


import com.example.unlimint.network.dao.JokeDao
import com.example.unlimint.network.service.ApiHelper
import com.example.unlimint.network.utils.Resource
import com.example.unlimint.utils.NetworkHelper
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow


class JokeRepository (private val apiHelper: ApiHelper, private val jokeDao: JokeDao, val networkHelper: NetworkHelper
) {
    fun getJokes() = flow<Resource<String>> {
        if(networkHelper.isNetworkConnected()) {
            emit(Resource.loading(null))
            emit(Resource.success(getJoke().body().toString()))
        } else {
            emit(Resource.error("Internet not connected"))
        }
        }.catch {
            emit(Resource.error(msg="Api failed"))
    }
    private suspend fun getJoke() = apiHelper.getJokes().run {
        if (this.isSuccessful && body() !== null) {
            jokeDao.getJoke(this)
            this
        } else {
            Resource.error("something is wrong",data = null)
        }
        this
    }
}