package com.lambda.mnemecards.network

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    var id: String? = "",
    var favSubjects: String? = "",

    @get:PropertyName("MobileOrDesktop")
    @set:PropertyName("MobileOrDesktop")
    var mobileOrDesktop: String? = "",
    var customOrPremade: String? = "",

    @get:PropertyName("notification-frequency")
    @set:PropertyName("notification-frequency")
    var notificationFrequency: String? = "",

    @get:PropertyName("study-frequency")
    @set:PropertyName("study-frequency")
    var studyFrequency: String? = "",

    var technique: String? = ""
):Parcelable