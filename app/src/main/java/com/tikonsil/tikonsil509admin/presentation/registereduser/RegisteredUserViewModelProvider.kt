package com.tikonsil.tikonsil509admin.presentation.registereduser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tikonsil.tikonsil509admin.domain.repository.registrereduser.RegistreredUserRepository

/** * Created by ISMOY BELIZAIRE on 13/05/2022. */
class RegisteredUserViewModelProvider(private val repository: RegistreredUserRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisteredUserViewModel(repository)as T
    }
}