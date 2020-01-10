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

class CardsViewModel(deck: Deck, app: Application):AndroidViewModel(app){

    private val _selectedDeck = MutableLiveData<Deck>()

    val selectedDeck:LiveData<Deck>
        get() = _selectedDeck

    init {
        _selectedDeck.value = deck
    }

    val displayDeckName = _selectedDeck.value?.deckName
    val displayDeckCardAmount = _selectedDeck.value?.data?.size

}