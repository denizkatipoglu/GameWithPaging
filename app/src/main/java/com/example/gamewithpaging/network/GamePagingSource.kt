package com.example.gamewithpaging.network

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.gamewithpaging.model.GameResults
import retrofit2.HttpException
import java.io.IOException

const val STARTING_PAGE_INDEX = 1

@ExperimentalPagingApi
class GamePagingSource(private val service: GameApi) : PagingSource<Int, GameResults>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GameResults> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response =
                service.getGamesList(page = page, size = params.loadSize, ordered = "released",
                    platforms = "4,5",key = "905bf28dea024135b163cb11b38ced30")
            LoadResult.Page(
                data = response.results!!,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (response.results.isEmpty()) null else page + 1
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