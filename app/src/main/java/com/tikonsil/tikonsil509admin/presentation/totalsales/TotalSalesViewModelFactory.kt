package com.tikonsil.tikonsil509admin.presentation.totalsales

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tikonsil.tikonsil509admin.domain.repository.totalsales.TotalSalesRepository

/** * Created by ISMOY BELIZAIRE on 13/05/2022. */
class TotalSalesViewModelFactory(private val repository: TotalSalesRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TotalSalesViewModel(repository) as T
    }

}