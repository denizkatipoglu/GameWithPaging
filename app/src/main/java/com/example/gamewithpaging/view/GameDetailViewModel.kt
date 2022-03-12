package com.example.gamewithpaging.view

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.example.gamewithpaging.core.BaseViewModel
import com.example.gamewithpaging.data.GamesRepository
import kotlinx.coroutines.launch

class GameDetailViewModel @ViewModelInject constructor(private val repository: GamesRepository) :
    ViewModel() {


    init {
        viewModelScope.launch {
        }
    }
}
