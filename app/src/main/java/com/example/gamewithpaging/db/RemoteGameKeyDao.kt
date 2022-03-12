package com.example.gamewithpaging.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteGameKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteGameKey: List<RemoteGameKey>)

    @Query("SELECT * FROM game_remote_keys WHERE id = :id")
    suspend fun remoteKeysGameId(id: String): RemoteGameKey?

    @Query("DELETE FROM game_remote_keys")
    suspend fun deleteAll()
}

