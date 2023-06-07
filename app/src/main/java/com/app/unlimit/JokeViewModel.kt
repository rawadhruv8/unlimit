package com.app.unlimit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.unlimit.db.JokesDao
import com.app.unlimit.retrofit.APIService
import com.app.unlimit.retrofit.ResponseJoke
import kotlinx.coroutines.*

class JokeViewModel(private val apiService: APIService, private val jokesDao: JokesDao) :
    ViewModel() {

    val observeJokeResponse = MutableLiveData<ResponseJoke>()
    val observeLocalJokes = MutableLiveData<ArrayList<ResponseJoke>>()

    val observeError = MutableLiveData<Any>()
    var job: Job? = null

    fun callJokesApi() {
        job = viewModelScope.launch {
            val response = apiService.getJokes()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    observeJokeResponse.value = response.body()
                    insertJokesInDb(response.body())
                }
            }
        }
    }

    private fun insertJokesInDb(it: ResponseJoke?) {
        CoroutineScope(Dispatchers.IO).launch {
            it.let {
                jokesDao.insertData(it!!)
            }
        }
    }

    fun getAllLocalJokes() {
        CoroutineScope(Dispatchers.IO).launch {
            val tempList = jokesDao.getJokesList()
            val list = if (tempList.isNotEmpty())
                jokesDao.getJokesList().reversed() as ArrayList<ResponseJoke> else ArrayList()

            observeLocalJokes.postValue(list)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}