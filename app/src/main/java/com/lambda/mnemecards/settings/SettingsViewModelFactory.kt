package com.lambda.mnemecards.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lambda.mnemecards.network.User

// You need a view model factory for when passing arguments
class SettingsViewModelFactory(private val name: String?, private val photo: String?, private val user: User?) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(name, photo, user) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}