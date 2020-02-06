package com.lambda.mnemecards.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lambda.mnemecards.network.*
import kotlinx.coroutines.*
import java.lang.Exception

class HomeViewModel(name: String?, photo: String?, user:User?) : ViewModel() {

    // Internally, we use a MutableLiveData, because we will be updating the List of Decks
    // with new values
    val _decks = MutableLiveData<MutableList<Deck>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val decks: LiveData<MutableList<Deck>>
        get() = _decks

    private var _deckNames = MutableLiveData<List<DeckInformation>>()

    // Internally, we use a MutableLiveData to handle navigation to the selected property
    private val _navigateToSelectedDeck = MutableLiveData<Deck>()

    // The external immutable LiveData for the navigation property
    val navigateToSelectedDeck: LiveData<Deck>
        get() = _navigateToSelectedDeck

    private val _navigateToEditSelectedDeck = MutableLiveData<Deck>()

    val navifateToEditSelectedDeck: LiveData<Deck>
        get() = _navigateToEditSelectedDeck

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    // Holds the decks that will be used to reinitialize _decks.
    // Since working with live data lists have to copy the initial value, modify it, and then set it again
    val newDeck = mutableListOf<Deck>()

    // The progressBar that filled up as more cards are mastered
    private val _progressBar = MutableLiveData<Int>()

    val progressBar: LiveData<Int>
        get() = _progressBar

    private var _username = MutableLiveData<String>()
    val username: LiveData<String>
        get() = _username

    var name: String?
    var photo: String?
    var user: User?

    init {

        this.name = name
        this.photo = photo
        this.user = user

        coroutineScope.launch {
            getDeckNames()
            delay(200)
//            _deckNames.value?.get(0)?.let { getDecks(it) }
            for (deck in _deckNames.value!!) {
                getDecks(deck.deckName)
            }

            // Whenever using postValue inside of thread, need a delay or else logs will be null.
            val listOfData = mutableListOf<Data>()
            listOfData.add(Data(DataX("Front12", "Back12")))
            val newDeckStatic = Deck(listOfData, "Deck123", "", "", 0)
            newDeck.add(newDeckStatic)
            _decks.postValue(newDeck)
            delay(100)
        }
    }

    fun displayDeckDetails(selectedDeck: Deck){
            _navigateToSelectedDeck.value = selectedDeck
    }

    fun editDeckDetails(selectedDeck: Deck){
        _navigateToEditSelectedDeck.value = selectedDeck
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedProperty is set to null
     */
    fun displayDeckDetailsComplete(){
        _navigateToSelectedDeck.value = null
    }

    fun displayDeckEditComplete(){
        _navigateToEditSelectedDeck.value = null
    }

    private suspend fun getDeckNames() {

        var getDecksDeffered = DeckApi.retrofitService.getDemoDecks(
            "I2r2gejFYwCQfqafWlVy"
        )
        try {

            val deckResult = getDecksDeffered.await()

            _deckNames.value = deckResult

        } catch (e: Exception) {
            Log.i("HomeViewModel CATCH", "${e.message}")
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


            // Need this delay or else the value will be null when using logs
            delay(100)

//            newDecks.postValue(_decks.value)

        } catch (e: Exception) {
            Log.i("HomeViewModel CATCH", "${e.message}")
        }
    }

}





