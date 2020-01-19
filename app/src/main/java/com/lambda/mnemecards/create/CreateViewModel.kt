package com.lambda.mnemecards.create

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lambda.mnemecards.network.Card
import com.lambda.mnemecards.network.DataX
import com.lambda.mnemecards.network.Deck

class CreateViewModel(decks: MutableList<Deck>, app: Application) : AndroidViewModel(app){

    private val _decks = MutableLiveData<MutableList<Deck>>()
    val decks : LiveData<MutableList<Deck>>
        get() = _decks

    private val _cards = MutableLiveData<List<DataX>>()
    val cards: LiveData<List<DataX>>
        get() = _cards

    var cardFront : String = ""
    var cardBack : String = ""

    val listResult = mutableListOf<DataX>()

    init {
        this._decks.value = decks

//        cardFront = "Testing Front"
//        cardBack = "Testing Back"

//        val listResult = mutableListOf<DataX>(DataX(cardFront, cardBack))

        addCards()
        _cards.value = listResult
    }

//    fun displayCardDetails(card: DataX){
//        _navigateToSelectedDeck.value = selectedDeck
//    }

    fun addCards(){
        listResult.add(DataX("Front", "Back"))
        _cards.value = listResult
    }

}