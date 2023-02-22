package com.example.unlimint.viewmodel

import androidx.lifecycle.*
import com.example.unlimint.network.repository.JokeRepository
import com.example.unlimint.network.utils.Resource
import com.example.unlimint.utils.NetworkHelper
import kotlinx.coroutines.*
import kotlin.time.DurationUnit
import kotlin.time.toDuration


class MainViewModel (private val repository: JokeRepository, private val networkHelper: NetworkHelper): ViewModel() {

    private var _joke = MutableLiveData<Resource<String>>()
    val joke: LiveData<Resource<String>> = _joke
    private val job = startRepeatingJoke(5)

    init {
        if (networkHelper.isNetworkConnected()) {
            job.start()
        }
    }

    private fun startRepeatingJoke(timeInterval: Long): Job {
        return CoroutineScope(Dispatchers.IO).launch {
            while (NonCancellable.isActive) {
               jokes()
                delay(timeInterval.toDuration(DurationUnit.SECONDS))
            }
        }
    }

    fun startJob() = startRepeatingJoke(5)
    fun stopJob() = job.cancel()

    fun jokes() {
        viewModelScope.launch {
            repository.getJokes().collect{
                _joke.postValue(it)
            }
        }
    }
}