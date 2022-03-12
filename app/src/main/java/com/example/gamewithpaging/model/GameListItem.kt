package com.example.gamewithpaging.model

sealed class GameListItem {
    data class GameItem(val results: Game) : GameListItem()
}