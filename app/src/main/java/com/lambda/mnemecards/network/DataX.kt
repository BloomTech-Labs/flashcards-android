package com.lambda.mnemecards.network


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

// This class holds data about the actual cards' value
@Parcelize
@JsonClass(generateAdapter = true)
data class DataX(
    @Json(name = "front")
    val front: String,

    @Json(name = "back")
    val back: String,

    var checked: Boolean = false
):Parcelable