package com.lambda.mnemecards.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Card(
    val id: String,
    val front: String,
    val back: String
): Parcelable