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
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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
    fun getDemoDecks(@Path("deckid") deckid: String): Deferred<List<String>>

    // Only need the demo deck id  and deck name
    @GET("api/demo/{deckid}/{deckname}")
    fun getDemoCards(@Path("deckid") deckid:String,
                     @Path("deckname") deckname: String):Deferred<Deck>
    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result
            //Deferred<List<MarsProperty>>

    @GET("api/demo/I2r2gejFYwCQfqafWlVy/Biology")
    fun getDemoCardsEasy(): Deferred<Deck>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object DeckApi {
    val retrofitService : DeckApiService by lazy { retrofit.create(DeckApiService::class.java) }
}