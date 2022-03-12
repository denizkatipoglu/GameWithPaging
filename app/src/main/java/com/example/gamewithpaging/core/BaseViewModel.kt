package com.example.gamewithpaging.core

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.map
import com.example.gamewithpaging.model.GameResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class BaseViewModel : ViewModel() {
    abstract val dataSource: Flow<PagingData<GameResults>>

    val games: Flow<PagingData<GameResults>> by lazy {
        dataSource
            .map { pagingData -> pagingData.map { GameResults(it.id,it.name,it.backgroundImage!!,it.ratingCounts!!,it.rating,it.releasedDate!!) } }
    }

}