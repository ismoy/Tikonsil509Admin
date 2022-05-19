package com.tikonsil.tikonsil509admin.presentation.historysales

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tikonsil.tikonsil509admin.domain.repository.historysales.HistorySalesRepository

/** * Created by ISMOY BELIZAIRE on 14/05/2022. */
class HistorySalesViewModelProvider(private val repository: HistorySalesRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HistorySalesViewModel(repository) as T
    }
}