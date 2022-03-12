package com.example.gamewithpaging.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonReader
import java.io.Serializable

@Entity(tableName = "games_result")
@JsonClass(generateAdapter = true)
data class GameResults(

    @PrimaryKey
    val id: String,

    @SerializedName("name")
    val name: String,

    @Json(name ="background_image")
    val backgroundImage: String?= "",

    @Json(name ="rating_counts")
    val ratingCounts: String?= "",

    @SerializedName("released")
    val releasedDate: String?= ""
) : Serializable
