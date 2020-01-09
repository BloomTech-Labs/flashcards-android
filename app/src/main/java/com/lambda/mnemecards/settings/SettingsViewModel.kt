package com.lambda.mnemecards.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel(photoUrl: String): ViewModel(){

    private val _photo = MutableLiveData<String>()
    val photo: LiveData<String>
        get() = _photo

    init {
        _photo.value = photoUrl
    }

}