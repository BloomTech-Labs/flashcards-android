package com.lambda.mnemecards.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lambda.mnemecards.network.*
import kotlinx.coroutines.*
import java.lang.Exception

class HomeViewModel : ViewModel() {

    // Internally, we use a MutableLiveData, because we will be updating the List of Decks
    // with new values
    private val _decks = MutableLiveData<MutableList<Deck>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val decks: LiveData<MutableList<Deck>>
        get() = _decks

    private var _deckNames = MutableLiveData<List<String>>()

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val newDeck = mutableListOf<Deck>()

    init {
        coroutineScope.launch {
            getDeckNames()
//            _deckNames.value?.get(0)?.let { getDecks(it) }
            for (name in _deckNames.value!!) {
                getDecks(name)
            }
        }
    }

    private suspend fun getDeckNames() {

        var getDecksDeffered = DeckApi.retrofitService.getDemoDecks(
            "I2r2gejFYwCQfqafWlVy"
        )
        try {

            val deckResult = getDecksDeffered.await()

            _deckNames.value = deckResult

            Log.i("HomeViewModel name TRY", "${_deckNames.value!!.get(0)}")
            Log.i("HomeViewModel name TRY", "${_deckNames.value!!.get(1)}")

        } catch (e: Exception) {
            Log.i("HomeViewModel nm CATCH", "${e.message}")
        }
    }

    private suspend fun getDecks(deckName: String) {

//        val cards = listOf<Card>(Card("1","front","back"), Card("2","frontt","backk"), Card("3", "fronttt", "backkk"))
//        val listResult = listOf<Deck>(Deck("Name", cards, "testing"),Deck("Name", cards, "testing"),Deck("Name", cards, "testing"))


//        _decks.value = listResult

        var getCardsDeffered = DeckApi.retrofitService.getDemoCards(
            "I2r2gejFYwCQfqafWlVy",
            deckName
        )

//            api/demo/I2r2gejFYwCQfqafWlVy/Biology
//            var getCardsEasyDeffered = DeckApi.retrofitService.getDemoCardsEasy()

        try {

            val deckResult = getCardsDeffered.await()

            newDeck.add(deckResult)

            // Use .postValue() when working inside of a thread
            // Otherwise use .value() when working outside

            _decks.postValue(newDeck)
//            _decks.value?.add(deckResult)

            Log.i("HomeViewModel Try", "${deckResult}")
            Log.i("HomeViewModel Try", "${newDeck}")
            Log.i("HomeViewModel Try", "${_decks.value?.get(0)?.deckName}")

            // Need this delay or else the value will be null
            delay(1000)

            Log.i("HomeViewModel Tryyy", "${_decks.value?.get(0)?.deckName}")
            Log.i("HomeViewModel Tryyy", "${_decks.value?.get(1)?.deckName}")


        } catch (e: Exception) {
            Log.i("HomeViewModel get CATCH", "${e.message}")
        }
    }

}





