package com.lambda.mnemecards.network


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DeckInformation(
    @Json(name = "deckName")
    val deckName: String,
    @Json(name = "demo")
    val demo: Boolean
)