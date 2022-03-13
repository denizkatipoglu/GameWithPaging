package com.example.gamewithpaging.network

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.gamewithpaging.Utils.mergedList
import com.example.gamewithpaging.model.GameResults
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import java.io.IOException

const val STARTING_PAGE_INDEX = 1

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
class GamePagingSource(private val service: GameApi) : PagingSource<Int, GameResults>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GameResults> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            var response: List<GameResults> = mutableListOf()

            val f1 = flow {
                emit(
                    service.getGamesList(
                        page = page, size = 10, ordered = "-released",
                        metacritic = "10,100",platforms = "5", key = "905bf28dea024135b163cb11b38ced30"
                    )
                )
            }
            val f2 = flow {
                emit(
                    service.getGamesList(
                        page = page, size = 10, ordered = "-released",
                        metacritic = "10,100",platforms = "4", key = "905bf28dea024135b163cb11b38ced30"
                    )
                )
            }
            merge(f1, f2).collect { game ->
                response = mergedList(
                    response,
                    game.results!!
                ).sortedWith(compareByDescending { it.releasedDate })
            }

            LoadResult.Page(
                data = response,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GameResults>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}