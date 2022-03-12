package com.example.gamewithpaging.di

import android.content.Context
import androidx.room.Room
import com.example.gamewithpaging.db.GameDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbGameModule {
    @Provides
    @Singleton
    fun provideDb(
        @ApplicationContext appContext: Context
    ): GameDatabase {
        return Room.databaseBuilder(
            appContext, GameDatabase::class.java,
            "Game.db"
        ).build()
    }
}