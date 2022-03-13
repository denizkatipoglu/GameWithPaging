package com.example.gamewithpaging.view.gamedetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.gamewithpaging.model.GameDetailModel
import com.example.gamewithpaging.model.GameResults
import com.example.gamewithpaging.network.GameApi
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.combine

open class GameDetailViewModel @ViewModelInject constructor(private val api: GameApi) :
    ViewModel() {

    private val job = SupervisorJob()
    private val main: CoroutineDispatcher = Dispatchers.Main
    protected val io: CoroutineDispatcher = Dispatchers.IO
    private val gameDetail: MutableLiveData<GameDetailModel> = MutableLiveData()
    private val gameDummyDetail: MutableLiveData<GameDetailModel> = MutableLiveData()

    fun getGameDetail(): LiveData<GameDetailModel> = gameDetail

    fun getGameDummyDetail(): LiveData<GameDetailModel> = gameDummyDetail

    init {
        viewModelScope.launch {
        }
    }

    fun loadGameDetail(gameId: String) = GlobalScope.launch(main + job) {
        val response = api.getGameDetail(gameId, key = "905bf28dea024135b163cb11b38ced30")
        gameDetail.postValue(response)
        val response1 = api.getGameDetail(gameId, key = "905bf28dea024135b163cb11b38ced30")
        gameDummyDetail.postValue(response1)
    }
    val titleFlow = combine(gameDetail.asFlow(), gameDummyDetail.asFlow()){ profile, user ->
        "${profile.name+"11111111"} ${user.name}"
    }
}
