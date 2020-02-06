package com.lambda.mnemecards.network

import androidx.lifecycle.MutableLiveData
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "https://flashcards-be.herokuapp.com/"

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

/**
 * A public interface that exposes the [getProperties] method
 */
interface DeckApiService {
    /**
     * Returns a Coroutine [Deferred] [List] of [Deck] which can be fetched with await() if
     * in a Coroutine scope.
     */

    // For some reason making this return type: Deferred<ArrayList<String>>
    // Gets an list of all the deck names
    @GET("api/demo/{deckid}")
    fun getDemoDecks(@Path("deckid") deckid: String): Deferred<List<DeckInformation>>

    // Only need the demo deck id  and deck name
    @GET("api/demo/{deckid}/{deckname}")
    fun getDemoCards(@Path("deckid") deckid:String,
                     @Path("deckname") deckname: String):Deferred<Deck>
    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result
            //Deferred<List<MarsProperty>>

    // Used to add a deck to the backend.
    @POST("api/deck/{id}/{deckName}")
    fun addDeck(@Path("id") userId:String,
                @Path("deckName") deckName: String)

    // Used to add cards to an existing deck
    @POST("api/deck/{id}/{deckName}/add")
    fun addCards(@Path("id") userId: String,
                 @Path("deckName")deckName: String)

    // Used to update a card in the deck.
    @PUT("api/deck/update/{id}/{deckName}")
    fun updateCard(@Path("id") userId: String,
                   @Path("deckName") deckName: String)

    // Used to update the name of a deck.
    @PUT("api/deck/update-deck-name/{id}/{deckName}")
    fun updateDeck(@Path("id") userId: String,
                   @Path("deckName") deckName: String)

    // Used to delete a card from a deck.
    @DELETE("api/deck/{id}/{deckName}/delete-cards")
    fun deleteCard(@Path("id") userId: String,
                   @Path("deckName") deckId: String)

    // Used to delete a deck.
    @DELETE("api/deck/{id}/{deckName}/delete-deck")
    fun deleteDeck(@Path("id") userId: String,
                   @Path("deckName") deckid: String)
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object DeckApi {
    val retrofitService : DeckApiService by lazy { retrofit.create(DeckApiService::class.java) }
}