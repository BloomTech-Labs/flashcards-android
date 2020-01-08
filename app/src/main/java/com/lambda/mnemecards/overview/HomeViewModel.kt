package com.lambda.mnemecards.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lambda.mnemecards.network.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import java.lang.Exception

class HomeViewModel: ViewModel(){

    // Internally, we use a MutableLiveData, because we will be updating the List of Decks
    // with new values
    private val _decks = MutableLiveData<Deck>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val decks: LiveData<Deck>
        get() = _decks

    private var _deckNames = MutableLiveData<List<String>>()

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getDeckNames()
//        getDecks()
    }

    private fun getDeckNames(){

        coroutineScope.launch {
            var getDecksDeffered = DeckApi.retrofitService.getDemoDecks(
                "I2r2gejFYwCQfqafWlVy"
            )
            try{

                val deckResult = getDecksDeffered.await()

                _deckNames.value = deckResult

                Log.i("HomeViewModel TRY", "${_deckNames.value!!.get(0)}")

                coroutineScope.launch {
                    for(deckName in _deckNames.value!!){
                        getDecks(deckName)
                    }
                }
            }catch (e: Exception){
                Log.i("HomeViewModel CATCH", "${e.message}")
            }
        }

    }

    private fun getDecks(deckName: String){

//        val cards = listOf<Card>(Card("1","front","back"), Card("2","frontt","backk"), Card("3", "fronttt", "backkk"))
//        val listResult = listOf<Deck>(Deck("Name", cards, "testing"),Deck("Name", cards, "testing"),Deck("Name", cards, "testing"))


//        _decks.value = listResult

        coroutineScope.launch {

            var getCardsDeffered = DeckApi.retrofitService.getDemoCards(
                "I2r2gejFYwCQfqafWlVy",
                deckName)

//            api/demo/I2r2gejFYwCQfqafWlVy/Biology
//            var getCardsEasyDeffered = DeckApi.retrofitService.getDemoCardsEasy()

            try{

                val listResult = getCardsDeffered.await()

                _decks.value = listResult

                Log.i("HomeViewModel TRY", "${_decks.value}")
                Log.i("HomeViewModel TRY", "${_decks.value!!.deckName}")


            }catch (e: Exception){
                Log.i("HomeViewModel CATCH", "${e.message}")
            }
        }


    }

}