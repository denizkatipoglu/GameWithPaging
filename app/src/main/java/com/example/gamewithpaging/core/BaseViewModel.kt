package com.example.gamewithpaging.core

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.gamewithpaging.model.GameResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class BaseViewModel : ViewModel() {
    abstract val dataSource: Flow<PagingData<GameResults>>

    val games: Flow<PagingData<GameResults>> by lazy {
        dataSource
            .map { pagingData ->
                pagingData.map {
                    GameResults(
                        it.id,
                        it.name,
                        if (it.backgroundImage != null && !TextUtils.isEmpty(it.backgroundImage)) it.backgroundImage else "",
                        it.ratingCounts!!,
                        it.rating,
                        it.releasedDate
                    )
                }

            }.cachedIn(viewModelScope)
    }

}