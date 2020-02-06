package com.lambda.mnemecards.network

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

// This class contains all of the information about a deck and the list of cards that they have.

@Parcelize
@JsonClass(generateAdapter = true)
data class Deck(
    @Json(name = "data")
    val `data`: MutableList<Data>,

    @Json(name = "deckName")
    var deckName: String,

    val imgSrcUrl: String?,

    // Used to display the first card in the HomeFragment
    var frontCard:String = "Testing",

    // Used to display the progress of mastery in the HomeFragment
    var progress:Int = 0
): Parcelable{

    init {
        frontCard = data[0].data.front
    }
}