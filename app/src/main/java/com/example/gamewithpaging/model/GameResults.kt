package com.example.gamewithpaging.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@Entity(tableName = "games_result")
@JsonClass(generateAdapter = true)
data class GameResults(

    @PrimaryKey
    val id: String,

    val name: String,

    @Json(name ="background_image")
    val backgroundImage: String?= "",

    @Json(name ="ratings_counts")
    val ratingCounts: Int?= 0,

    @Json(name ="rating")
    val rating: String?= "",

    @Json(name ="released")
    val releasedDate: String?= ""
) : Serializable
