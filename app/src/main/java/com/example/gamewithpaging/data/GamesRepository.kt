package com.example.gamewithpaging.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.gamewithpaging.db.GameDatabase
import com.example.gamewithpaging.model.GameResults
import com.example.gamewithpaging.network.GameApi
import com.example.gamewithpaging.network.GamePagingSource
import com.example.gamewithpaging.network.GameRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val PAGE_SIZE = 10

class GamesRepository @Inject constructor(
    private val gameApi: GameApi,
    private val database: GameDatabase
) {
    @ExperimentalPagingApi
    fun getGamesFromNetwork(): Flow<PagingData<GameResults>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = PAGE_SIZE + (PAGE_SIZE * 2),
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GamePagingSource(gameApi) }
        ).flow
    }

    @ExperimentalPagingApi
    fun getGamesFromDb(): Flow<PagingData<GameResults>> {
        val pagingSourceFactory = { database.getGameDao().getAll() }

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = PAGE_SIZE + (PAGE_SIZE * 2),
                enablePlaceholders = false
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    @ExperimentalPagingApi
    fun getGamesFromMediator(): Flow<PagingData<GameResults>> {
        val pagingSourceFactory = { database.getGameDao().getAll() }

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = PAGE_SIZE + (PAGE_SIZE * 2),
                enablePlaceholders = false,
            ),
            remoteMediator = GameRemoteMediator(
                gameApi,
                database
            ),

            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    suspend fun fillWithDummyGames(dummyGames: List<GameResults>) {
        database.getGameDao().deleteAll()
        database.getGameDao().insertAll(dummyGames)
    }

    suspend fun deleteDummyData() {
        database.getGameDao().deleteAll()
    }
}