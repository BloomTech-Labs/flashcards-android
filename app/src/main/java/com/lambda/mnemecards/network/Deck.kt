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
    var deckName: String,

    val imgSrcUrl: String?,

    // Used to display the first card in the HomeFragment
    var frontCard:String = "Testing"
): Parcelable{
    
    init {
        frontCard = data[0].data.front
    }
}