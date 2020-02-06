package com.lambda.mnemecards.create

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lambda.mnemecards.network.Data
import com.lambda.mnemecards.network.DataX
import com.lambda.mnemecards.network.Deck
import com.lambda.mnemecards.overview.HomeViewModel

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

    var cardsResult = mutableListOf<DataX>()

    var decksResult = mutableListOf<Deck>()

    init {

        _decks.value = decks

        decksResult = decks
    }

    // Adds a card to the list of cards
    fun addCards(front: String, back: String){
        cardsResult.add(DataX(front, back))
        _cards.value = cardsResult
    }

    fun addDeck(deckName: String){
//        val listData = mutableListOf<Data>()
//       for(data in cardsResult){
//            listData.add(Data(data))
//       }
//        decksResult.add(Deck(listData, deckName, "", "", 0))
    }
}