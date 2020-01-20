package com.lambda.mnemecards.create

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lambda.mnemecards.network.Card
import com.lambda.mnemecards.network.DataX
import com.lambda.mnemecards.network.Deck

class CreateViewModel(decks: MutableList<Deck>, app: Application) : AndroidViewModel(app){

    var counter = 0

    private val _decks = MutableLiveData<MutableList<Deck>>()
    val decks : LiveData<MutableList<Deck>>
        get() = _decks

    private val _cards = MutableLiveData<List<DataX>>()
    val cards: LiveData<List<DataX>>
        get() = _cards

    // Recycler View wouldn't update the value if not using a LiveData variable in the XML
//    var normieCardList = listOf<DataX>()

    val listResult = mutableListOf<DataX>()

    init {

    }

    fun addCards(front: String, back: String){
        listResult.add(DataX(front, back))
        _cards.value = listResult
    }
}