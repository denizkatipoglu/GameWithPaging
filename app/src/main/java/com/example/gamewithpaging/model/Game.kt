package com.example.gamewithpaging.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.io.Serializable

@Entity(tableName = "games")
@JsonClass(generateAdapter = true)
data class Game(
    @PrimaryKey
    val id: String?,
    val results: List<GameResults>? = null

) : Serializable
