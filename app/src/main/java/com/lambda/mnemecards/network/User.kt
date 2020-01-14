package com.lambda.mnemecards.network

import com.google.firebase.firestore.PropertyName

data class User (
    val id: String? = "",
    var favSubjects: String? = "",

    @PropertyName("MobileOrDesktop")
    var mobileOrDesktop: String? = "",
    var customOrPremade: String? = "",

    @PropertyName("notification-frequency")
    var notificationFrequency: String? = "",

    @PropertyName("study-frequency")
    var studyFrequency: String? = "",

    var techniques: String? = ""
)