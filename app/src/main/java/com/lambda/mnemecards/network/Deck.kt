package com.lambda.mnemecards.network

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Deck(
    @Json(name = "data")
    val `data`: List<Data>,

    @Json(name = "deckName")
    val deckName: String,

    val imgSrcUrl: String?
): Parcelable