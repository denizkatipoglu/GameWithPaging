package com.example.gamewithpaging.network

import com.example.gamewithpaging.model.Game
import retrofit2.http.GET
import retrofit2.http.Query

interface GameApi {

    //  https://api.rawg.io/api/games?page_size=10&page=1&key=905bf28dea024135b163cb11b38ced30

    @GET("games")
    suspend fun getGamesList(
        @Query("page_size") size: Int,
        @Query("page") page: Int,
        @Query("ordering") ordered: String,
        @Query("key") key: String
    ): Game
}