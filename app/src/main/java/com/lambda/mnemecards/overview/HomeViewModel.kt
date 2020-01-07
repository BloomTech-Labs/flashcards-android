package com.lambda.mnemecards.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lambda.mnemecards.network.Card
import com.lambda.mnemecards.network.Deck

class HomeViewModel: ViewModel(){

    // Internally, we use a MutableLiveData, because we will be updating the List of Decks
    // with new values
    private val _decks = MutableLiveData<List<Deck>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val decks: LiveData<List<Deck>>
        get() = _decks

    init {
        getDecks()
    }

    private fun getDecks(){

        val cards = listOf<Card>(Card("1","front","back"), Card("2","frontt","backk"))
        val listResult = listOf<Deck>(Deck("Name", cards, "testing"))

        _decks.value = listResult
    }
}