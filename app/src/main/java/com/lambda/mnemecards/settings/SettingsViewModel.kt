package com.lambda.mnemecards.settings

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lambda.mnemecards.network.User

class SettingsViewModel(name:String?, photoUrl: String?, user: User?): AndroidViewModel(Application()){

    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private val _photo = MutableLiveData<String>()
    val photo: LiveData<String>
        get() = _photo

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    init {
        _name.value = name
        _photo.value = photoUrl
        _user.value = user
    }

    fun changeTheName(){
        _name.value = "PLEASE WORKKKK"
        Log.i("SettingsViewModel", _name.value)
    }

}