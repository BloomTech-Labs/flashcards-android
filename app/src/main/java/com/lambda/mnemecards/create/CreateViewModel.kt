package com.lambda.mnemecards.create

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lambda.mnemecards.network.Deck

class CreateViewModel(decks: MutableList<Deck>, app: Application) : AndroidViewModel(app){

    private val _decks = MutableLiveData<MutableList<Deck>>()
    val decks : LiveData<MutableList<Deck>>
        get() = _decks

    init {
        this._decks.value = decks
    }

}