package com.example.gamewithpaging.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gamewithpaging.model.GameResults

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(games: List<GameResults>)

    @Query("SELECT * FROM games_result")
    fun getAll(): PagingSource<Int, GameResults>

    @Query("SELECT * FROM games_result")
    fun getAllAsList(): List<GameResults>

    @Query("DELETE FROM games_result")
    suspend fun deleteAll()

    @Query("SELECT * FROM games_result ORDER BY rating DESC")
    fun getGamesOrderByReleasedAsc():PagingSource<Int, GameResults>
}