package com.example.lahoradelidiota.localList.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class IdiotaViewModelFactory(private val repository: IdiotaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IdiotaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return IdiotaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
