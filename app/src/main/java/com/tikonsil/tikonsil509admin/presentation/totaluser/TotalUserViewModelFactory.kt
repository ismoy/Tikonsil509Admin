package com.tikonsil.tikonsil509admin.presentation.totaluser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tikonsil.tikonsil509admin.domain.repository.totaluser.TotalUserRepository

/** * Created by ISMOY BELIZAIRE on 13/05/2022. */
class TotalUserViewModelFactory(private val repository: TotalUserRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TotalUserViewModel(repository) as T
    }

}