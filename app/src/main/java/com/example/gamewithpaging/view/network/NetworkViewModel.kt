package com.example.gamewithpaging.view.network

import androidx.hilt.lifecycle.ViewModelInject
import androidx.paging.ExperimentalPagingApi
import com.example.gamewithpaging.core.BaseViewModel
import com.example.gamewithpaging.data.GamesRepository

@ExperimentalPagingApi
class NetworkOnlyViewModel @ViewModelInject constructor(private val repository: GamesRepository) :
    BaseViewModel() {
    override val dataSource = repository.getGamesFromNetwork()
}