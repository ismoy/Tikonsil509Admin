package com.tikonsil.tikonsil509admin.presentation.lastsales

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tikonsil.tikonsil509.domain.model.LastSales
import com.tikonsil.tikonsil509admin.domain.repository.lastsales.LastSalesRepository
import kotlinx.coroutines.launch
import retrofit2.Response

/** * Created by ISMOY BELIZAIRE on 12/05/2022. */
class LastSalesViewModel(private val repository: LastSalesRepository):ViewModel() {

    var isExistSnapshot = MutableLiveData<Boolean>()

    init {
       isExistSnapshot = repository.isExistSnapshot
    }

    fun getLastSales(): LiveData<MutableList<LastSales>> {
        val mutabledata =MutableLiveData<MutableList<LastSales>>()
        viewModelScope.launch {
            repository.getLastSales().observeForever{
                mutabledata.value = it
            }
        }
        return mutabledata
    }
}