package com.tikonsil.tikonsil509admin.presentation.countryprice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tikonsil.tikonsil509admin.domain.repository.countryprice.CountryPriceRepository

/** * Created by ISMOY BELIZAIRE on 15/05/2022. */
class CountryPriceViewModelProvider(private val repository: CountryPriceRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CountryPriceViewModel(repository) as T
    }
}