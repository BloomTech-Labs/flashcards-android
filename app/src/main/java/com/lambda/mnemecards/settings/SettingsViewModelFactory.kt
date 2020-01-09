package com.lambda.mnemecards.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// You need a view model factory for when passing arguments
class SettingsViewModelFactory(private val photo: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(photo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}