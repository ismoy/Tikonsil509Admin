package com.tikonsil.tikonsil509admin.presentation.lastsales

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tikonsil.tikonsil509admin.domain.repository.lastsales.LastSalesRepository

/** * Created by ISMOY BELIZAIRE on 23/04/2022. */
class LastSalesViewModelFactory(private val repository: LastSalesRepository):ViewModelProvider.Factory {
 override fun <T : ViewModel> create(modelClass: Class<T>): T {
  return LastSalesViewModel(repository) as T
 }

}