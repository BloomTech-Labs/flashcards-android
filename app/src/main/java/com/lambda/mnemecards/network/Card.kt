package com.lambda.mnemecards.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// This class isn't used, refer to DataX instead.

@Parcelize
data class Card(
    val id: String,
    val front: String,
    val back: String
): Parcelable