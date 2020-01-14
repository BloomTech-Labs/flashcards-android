package com.lambda.mnemecards.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lambda.mnemecards.network.User
import com.lambda.mnemecards.settings.SettingsViewModel

// You need a view model factory for when passing arguments
class HomeViewModelFactory(private val name: String?, private val photo: String?, private val user: User?) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(name, photo, user) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}