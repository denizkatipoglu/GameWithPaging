package com.example.gamewithpaging.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class TagsList(

    @Json(name = "image_background")
    val imageBackground: String = ""

) : Serializable
