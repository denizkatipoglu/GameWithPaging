package com.example.gamewithpaging.view.gamedetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamewithpaging.model.GameDetailModel
import com.example.gamewithpaging.network.GameApi
import kotlinx.coroutines.*

open class GameDetailViewModel @ViewModelInject constructor(private val api: GameApi) :
    ViewModel() {

    private val job = SupervisorJob()
    private val main: CoroutineDispatcher = Dispatchers.Main
    protected val io: CoroutineDispatcher = Dispatchers.IO
    private val gameDetail: MutableLiveData<GameDetailModel> = MutableLiveData()

    fun getGameDetail(): LiveData<GameDetailModel> = gameDetail

    init {
        viewModelScope.launch {
        }
    }

    fun loadGameDetail(gameId: String) = GlobalScope.launch(main + job) {
        val response = api.getGameDetail(gameId, key = "905bf28dea024135b163cb11b38ced30")
        gameDetail.postValue(response)
    }
}
