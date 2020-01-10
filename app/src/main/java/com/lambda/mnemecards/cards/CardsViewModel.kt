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

    init {
        _selectedDeck.value = deck
        _frontOrBack.value = true
        _displayCard.value = _selectedDeck.value?.data?.get(0)?.data?.front
    }

    val displayDeckName = _selectedDeck.value?.deckName
    val displayDeckCardAmount = _selectedDeck.value?.data?.size
    //var displayCard = _selectedDeck.value?.data?.get(0)?.data?.front

    fun displayCard() {
        
        _frontOrBack.value = !_frontOrBack.value!!

        if (_frontOrBack.value!!) {
            _displayCard.value = _selectedDeck.value?.data?.get(0)?.data?.front
        } else {
            _displayCard.value = _selectedDeck.value?.data?.get(0)?.data?.back
        }
    }

}