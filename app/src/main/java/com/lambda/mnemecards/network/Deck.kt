package com.lambda.mnemecards.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Deck(
    val name: String,
    val cards: List<Card>,
    val imgSrcUrl: String
): Parcelable