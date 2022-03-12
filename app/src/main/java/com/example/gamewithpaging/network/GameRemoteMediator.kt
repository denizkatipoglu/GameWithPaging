package com.example.gamewithpaging.network

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.gamewithpaging.db.GameDatabase
import com.example.gamewithpaging.db.RemoteGameKey
import com.example.gamewithpaging.model.GameResults
import okio.IOException
import retrofit2.HttpException

@ExperimentalPagingApi
class GameRemoteMediator(
    private val api: GameApi,
    private val db: GameDatabase
) : RemoteMediator<Int, GameResults>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, GameResults>
    ): MediatorResult {
        val pageKeyData = getKeyPageData(loadType, state)
        val page = when (pageKeyData) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        try {
            val response = api.getGamesList(page = page, size = state.config.pageSize,ordered = "released", key = "905bf28dea024135b163cb11b38ced30")
            val isEndOfList = response.results!!.isEmpty()
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.getGameDao().deleteAll()
                    db.getGameKeysDao().deleteAll()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
                val keys = response.results.map {
                    RemoteGameKey(it.id, prevKey = prevKey, nextKey = nextKey)
                }
                db.getGameKeysDao().insertAll(keys)
                db.getGameDao().insertAll(response.results)
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, GameResults>): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.nextKey
                return nextKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )
                prevKey
            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, GameResults>): RemoteGameKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id!!?.let { repoId ->
                db.getGameKeysDao().remoteKeysGameId(repoId)
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, GameResults>): RemoteGameKey? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { game -> db.getGameKeysDao().remoteKeysGameId(game.id) }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, GameResults>): RemoteGameKey? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { game -> db.getGameKeysDao().remoteKeysGameId(game.id) }
    }


}