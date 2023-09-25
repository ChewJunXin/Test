package com.example.earthsustain.database
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun insertUser(user: User) = viewModelScope.launch {
        userRepository.insertUser(user)
    }

    fun updateUser(user: User) = viewModelScope.launch {
        userRepository.updateUser(user)
    }

    fun deleteUser(user: User) = viewModelScope.launch {
        userRepository.deleteUser(user)
    }

    fun getUserById(userId: Int) = viewModelScope.launch {
        userRepository.getUserById(userId)
    }

    fun getAllUsers() = viewModelScope.launch {
        userRepository.getAllUsers()
    }
}