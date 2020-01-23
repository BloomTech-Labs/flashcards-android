package com.lambda.mnemecards.edit

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lambda.mnemecards.network.Deck

// You need a view model factory for when passing arguments
class EditViewModelFactory(
    private val deck: Deck,
    private val application: Application
): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditViewModel::class.java)) {
            return EditViewModel(deck, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}