package com.lambda.mnemecards.cards

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lambda.mnemecards.network.Deck

/**
 * Simple ViewModel factory that provides the Deck and context to the ViewModel.
 */
class CardsViewModelFactory(
    private val deck: Deck,
    private val application: Application
): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CardsViewModel::class.java)) {
            return CardsViewModel(deck, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}