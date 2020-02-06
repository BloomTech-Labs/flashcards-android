package com.lambda.mnemecards.network


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

// This class holds the data about a card and their id.
@Parcelize
@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "data")
    val `data`: DataX,
    @Json(name = "id")
    val id: String = ""
):Parcelable