package com.example.unlimint.viewmodel

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.os.postDelayed
import androidx.lifecycle.*
import com.example.unlimint.R
import com.example.unlimint.di.components.sharedpreferences.JokeSharedPref
import com.example.unlimint.network.repository.JokeRepository
import com.example.unlimint.network.utils.Resource
import com.example.unlimint.utils.NetworkHelper
import kotlinx.coroutines.*
import kotlin.time.DurationUnit
import kotlin.time.toDuration


class MainViewModel (private val jokeDao: JokeRepository,
                     private val networkHelper: NetworkHelper,
                     private val jokeSharedPref: JokeSharedPref,
                     private val context: Context): ViewModel() {

    private var _joke = MutableLiveData<Resource<String>>()
    val joke: LiveData<Resource<String>> = _joke

    private val job = startRepeatingJoke(60)

    init {
        if (networkHelper.isNetworkConnected()) {
            job.start()
        }
    }

    private fun startRepeatingJoke(timeInterval: Long): Job {
        return CoroutineScope(Dispatchers.Main.immediate).launch {
            while (NonCancellable.isActive) {
                getJoke()
                delay(timeInterval.toDuration(DurationUnit.SECONDS))
            }
        }
    }

    fun startJob() = startRepeatingJoke(60)

    fun stopRepeatingJoke() = job?.cancel()


    private fun getJoke() {
        viewModelScope.launch {
            _joke.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                jokeDao.getJoke().let {
                    if (it.isSuccessful) {
                        if (jokeSharedPref.getJoke().size <= 10) {
                            jokeSharedPref.saveJoke(it.body().toString())
                        }
                        _joke.postValue(Resource.success(it.body().toString()))
                    } else _joke.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } else {
                _joke.postValue(Resource.error(context.getString(R.string.no_internet), null))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}