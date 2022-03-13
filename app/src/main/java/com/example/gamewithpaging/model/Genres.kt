package com.example.gamewithpaging.model

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Genres(

    val name: String = ""

) : Serializable
