package com.example.earthsustain.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.earthsustain.database.EarthSustainRepository

class EarthSustainViewModelFactory(private val repository: EarthSustainRepository) : ViewModelProvider.Factory {
    fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EarthSustainViewModel::class.java)) {
            return EarthSustainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
