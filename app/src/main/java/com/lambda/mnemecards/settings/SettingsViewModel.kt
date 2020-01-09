package com.lambda.mnemecards.settings

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel(name:String?, photoUrl: String?): AndroidViewModel(Application()){

    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private val _photo = MutableLiveData<String>()
    val photo: LiveData<String>
        get() = _photo

    init {
        _name.value = name
    }

    fun changeTheName(){
        _name.value = "PLEASE WORKKKK"
        Log.i("SettingsViewModel", _name.value)
    }

}