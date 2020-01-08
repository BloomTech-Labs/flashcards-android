package com.lambda.mnemecards.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lambda.mnemecards.network.Card
import com.lambda.mnemecards.network.Deck
import com.lambda.mnemecards.network.DeckApi
import com.lambda.mnemecards.network.DeckApiService
import kotlinx.coroutines.*
import java.lang.Exception

class HomeViewModel: ViewModel(){

    // Internally, we use a MutableLiveData, because we will be updating the List of Decks
    // with new values
    private val _decks = MutableLiveData<Deck>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val decks: LiveData<Deck>
        get() = _decks

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getDecks()
    }

    private fun getDecks(){

//        val cards = listOf<Card>(Card("1","front","back"), Card("2","frontt","backk"), Card("3", "fronttt", "backkk"))
//        val listResult = listOf<Deck>(Deck("Name", cards, "testing"),Deck("Name", cards, "testing"),Deck("Name", cards, "testing"))


//        _decks.value = listResult

        coroutineScope.launch {

            var getCardsDeffered = DeckApi.retrofitService.getDemoCards(
                "I2r2gejFYwCQfqafWlVy",
                "Biology")

//            api/demo/I2r2gejFYwCQfqafWlVy/Biology
//            var getCardsEasyDeffered = DeckApi.retrofitService.getDemoCardsEasy()

            try{

                val listResult = getCardsDeffered.await()

                _decks.value = listResult

                Log.i("HomeViewModel", "${_decks.value}")


            }catch (e: Exception){
                Log.i("HomeViewModel", "${e.message}")
            }
        }


    }

}