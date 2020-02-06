package com.lambda.mnemecards.cards

import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import android.app.Application
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lambda.mnemecards.R
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

    private val _clickedEmoji = MutableLiveData<String>()
    val clickedEmoji: LiveData<String>
        get() = _clickedEmoji

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

    // Changes the display of the card depending on the view's value that's passed in.
    fun changeDisplay(view: View?){

        _frontOrBack.value = !_frontOrBack.value!!
        _frameLayoutColor.value = !_frameLayoutColor.value!!

        if(view?.tag.toString() == "btnNext"){
            increaseCardCount()
        }

        if(_frontOrBack.value!!){
            _displayCard.value = _selectedDeck.value?.data?.get(cardCounter)?.data?.front
        }
        else{
            _displayCard.value = _selectedDeck.value?.data?.get(cardCounter)?.data?.back
        }



    }

    // Get the tag of the ImageView and then set _clickedEmoji to that value.
    fun changeDisplayOnEmojiClick(view: View){

        Log.i("CardsViewModel", view.tag.toString())
        _clickedEmoji.value = view.tag.toString()

    }

    // Increases the card count. This is used for traversing through the deck.
    fun increaseCardCount(){
        if(cardCounter < _selectedDeck.value?.data?.size!!-1)
            cardCounter++
    }

    // Decreases the card count. Correctly not in use because of team decision to not be able to go back.
    fun decreaseCardCount(){
        if(cardCounter >= 0)
            cardCounter--
    }

}