package com.example.gamewithpaging.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gamewithpaging.model.GameResults

@Database(version = 1, entities = [GameResults::class, RemoteGameKey::class])
abstract class GameDatabase : RoomDatabase() {
    abstract fun getGameDao(): GameDao
    abstract fun getGameKeysDao(): RemoteGameKeyDao
}