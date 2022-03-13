package com.example.gamewithpaging.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class GameDetailModel(

    val id: String,

    val name: String,

    val description: String,

    @Json(name = "background_image")
    val backgroundImage: String? = "",

    @Json(name = "ratings_counts")
    val ratingCounts: Int? = 0,

    @Json(name = "rating")
    val rating: String? = "",

    @Json(name = "genres")
    val genres: List<Genres>? = null,

    @Json(name = "tags")
    val tags: List<TagsList>? = null,

    @Json(name = "added")
    val added: String? = "",

    @Json(name = "released")
    val releasedDate: String? = ""
) : Serializable
