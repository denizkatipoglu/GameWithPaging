package com.example.gamewithpaging.view.db

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.example.gamewithpaging.Constants
import com.example.gamewithpaging.core.BaseViewModel
import com.example.gamewithpaging.data.GamesRepository
import com.example.gamewithpaging.db.GameDatabase
import com.example.gamewithpaging.model.GameResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.invoke

import kotlinx.coroutines.launch

@ExperimentalPagingApi
class DataBaseViewModel @ViewModelInject constructor(
    private val repository: GamesRepository,
    private val database: GameDatabase
) :
    BaseViewModel() {

    override val dataSource = repository.getGamesFromDb()

    fun fillWithDummyGames() {

        val dummyGames = mutableListOf<GameResults>()
        GlobalScope.launch {
            Dispatchers.IO {
                if (database.getGameDao().getAllAsList().isNotEmpty()) {
                    with(database.getGameDao().getAllAsList()) {
                        for (i in 0 until size) {
                            dummyGames.add(
                                GameResults(
                                    get(i).id,
                                    get(i).name,
                                    get(i).backgroundImage,
                                    2,
                                    get(i).rating,
                                    get(i).releasedDate
                                )
                            )
                        }
                    }

                } else {
                    for (i in 0..10000) {
                        dummyGames.add(
                            GameResults(
                                i.toString(),
                                Constants.NO_DATA,
                                "https://media.rawg.io/media/games/942/9424d6bb763dc38d9378b488603c87fa.jpg",
                                123,
                                "3.43",
                                "2022-13-03"
                            )
                        )
                    }
                }
            }

            viewModelScope.launch {
                repository.fillWithDummyGames(dummyGames)
            }
        }
    }
}