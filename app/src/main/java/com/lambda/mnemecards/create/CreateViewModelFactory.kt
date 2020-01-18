package com.lambda.mnemecards.create

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lambda.mnemecards.network.Deck

class CreateViewModelFactory (private val decks: MutableList<Deck>,
private val application: Application
): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateViewModel::class.java)) {
            return CreateViewModel(decks, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}