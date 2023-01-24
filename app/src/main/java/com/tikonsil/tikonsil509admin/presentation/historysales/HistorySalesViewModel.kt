package com.tikonsil.tikonsil509admin.presentation.historysales

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tikonsil.tikonsil509admin.domain.model.Sales
import com.tikonsil.tikonsil509admin.domain.repository.historysales.HistorySalesRepository
import kotlinx.coroutines.launch

/** * Created by ISMOY BELIZAIRE on 14/05/2022. */
class HistorySalesViewModel(private val repository: HistorySalesRepository):ViewModel(){

    var isExistSnapshot = MutableLiveData<Boolean>()

    init {
      isExistSnapshot = repository.isExistSnapshot
    }

    fun getHistorySales(): LiveData<MutableList<Sales>> {
        val mutabledata = MutableLiveData<MutableList<Sales>>()
        viewModelScope.launch {
            repository.getHistorySales().observeForever{
                mutabledata.value = it
            }
        }
        return mutabledata
    }
}