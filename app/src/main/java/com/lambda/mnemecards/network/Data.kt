package com.lambda.mnemecards.network


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "data")
    val `data`: DataX,
    @Json(name = "id")
    val id: String
):Parcelable