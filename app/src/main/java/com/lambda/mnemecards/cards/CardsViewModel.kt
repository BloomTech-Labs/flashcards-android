package com.lambda.mnemecards.cards

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lambda.mnemecards.network.Deck

/**
 *  The [ViewModel] associated with the [HomeFragment], containing information about the selected
 *  [Deck].
 */

class CardsViewModel(deck: Deck, app: Application) : AndroidViewModel(app) {

    private val _selectedDeck = MutableLiveData<Deck>()

    val selectedDeck: LiveData<Deck>
        get() = _selectedDeck

    private val _frontOrBack = MutableLiveData<Boolean>()

    val frontOrBack: LiveData<Boolean>
        get() = _frontOrBack

    private val _displayCard = MutableLiveData<String>()

    val displayCard :LiveData<String>
        get() = _displayCard

    // True = White
    // False = Orange
    private val _frameLayoutColor = MutableLiveData<Boolean>()
    val frameLayoutColor: LiveData<Boolean>
        get() = _frameLayoutColor

    init {
        _selectedDeck.value = deck
        _frontOrBack.value = true
        _displayCard.value = _selectedDeck.value?.data?.get(0)?.data?.front
        _frameLayoutColor.value = true
    }

    val displayDeckName = _selectedDeck.value?.deckName
    val displayDeckCardAmount = _selectedDeck.value?.data?.size
    var cardCounter = 0

    fun changeDisplay(){

        _frontOrBack.value = !_frontOrBack.value!!
        _frameLayoutColor.value = !_frameLayoutColor.value!!

        if(_frontOrBack.value!!){
            _displayCard.value = _selectedDeck.value?.data?.get(cardCounter)?.data?.front
            increaseCardCount()
        }
        else{
            _displayCard.value = _selectedDeck.value?.data?.get(cardCounter)?.data?.back
            increaseCardCount()
        }

    }

    fun increaseCardCount(){
        if(cardCounter < _selectedDeck.value?.data?.size!!-1)
            cardCounter++
    }

}