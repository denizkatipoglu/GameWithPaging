package com.example.gamewithpaging.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_remote_keys")
data class RemoteGameKey(
    @PrimaryKey val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)
