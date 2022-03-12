package com.example.gamewithpaging.view.networkanddb

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.example.gamewithpaging.core.BaseViewModel
import com.example.gamewithpaging.data.GamesRepository
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class NetworkAndDbViewModel @ViewModelInject constructor(private val repository: GamesRepository) :
    BaseViewModel() {

    init {
        viewModelScope.launch {
            repository.deleteDummyData()
        }
    }

    override val dataSource = repository.getGamesFromMediator()
}
