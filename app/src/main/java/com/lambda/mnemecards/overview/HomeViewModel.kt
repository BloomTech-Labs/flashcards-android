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

    // Internally, we use a MutableLiveData to handle navigation to the selected property
    private val _navigateToSelectedDeck = MutableLiveData<Deck>()

    // The external immutable LiveData for the navigation property
    val navigateToSelectedDeck: LiveData<Deck>
        get() = _navigateToSelectedDeck

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    // Holds the decks that will be used to reinitialize _decks.
    // Since working with live data lists have to copy the initial value, modify it, and then set it again
    val newDeck = mutableListOf<Deck>()

    private var _username = MutableLiveData<String>()
    val username: LiveData<String>
        get() = _username

    init {
        coroutineScope.launch {
            getDeckNames()
//            _deckNames.value?.get(0)?.let { getDecks(it) }
            for (name in _deckNames.value!!) {
                getDecks(name)
            }

            // Whenever using postValue inside of thread, need a delay or else logs will be null.
            _decks.postValue(newDeck)
            delay(1000)
            Log.i("HomeViewModel tre", "${_decks.value?.get(0)?.deckName}")
            Log.i("HomeViewModel tre", "${_decks.value?.get(1)?.deckName}")
        }
    }

    fun setUsername(username:String){
        _username.value = username
    }

    fun displayDeckDetails(selectedDeck: Deck){
        _navigateToSelectedDeck.value = selectedDeck
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedProperty is set to null
     */
    fun displayDeckDetailsComplete(){
        _navigateToSelectedDeck.value = null
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


//            _decks.value?.add(deckResult)

            Log.i("HomeViewModel Try", "${deckResult}")
            Log.i("HomeViewModel Try", "${newDeck}")
            Log.i("HomeViewModel Try", "${_decks.value?.get(0)?.deckName}")

            // Need this delay or else the value will be null when using logs
            delay(1000)

            Log.i("HomeViewModel Tryyy", "${_decks.value?.get(0)?.deckName}")
            Log.i("HomeViewModel Tryyy", "${_decks.value?.get(1)?.deckName}")


        } catch (e: Exception) {
            Log.i("HomeViewModel get CATCH", "${e.message}")
        }
    }

}





