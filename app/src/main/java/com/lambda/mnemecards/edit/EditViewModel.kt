package com.lambda.mnemecards.edit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lambda.mnemecards.network.Data
import com.lambda.mnemecards.network.DataX
import com.lambda.mnemecards.network.Deck

class EditViewModel(deck: Deck, app: Application) : AndroidViewModel(app)  {

    private val _selectedDeck = MutableLiveData<Deck>()

    val selectedDeck : LiveData<Deck>
        get() = _selectedDeck

    private val _listOfCards = MutableLiveData<MutableList<DataX>>()
    val listOfCards : LiveData<MutableList<DataX>>
        get() = _listOfCards

    val obtainCards = mutableListOf<DataX>()

    init {
        _selectedDeck.value = deck

        for(data in _selectedDeck.value!!.data){
            obtainCards.add(data.data)
        }
        _listOfCards.value = obtainCards
    }

    // Adds a card to the list of cards
    fun addCard(front: String, back: String){
        obtainCards.add(DataX(front, back, false))
        _listOfCards.value = obtainCards
    }

}