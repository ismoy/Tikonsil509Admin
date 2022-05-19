package com.tikonsil.tikonsil509admin.presentation.totalsales

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tikonsil.tikonsil509admin.domain.model.TotalSales
import com.tikonsil.tikonsil509admin.domain.repository.totalsales.TotalSalesRepository

/** * Created by ISMOY BELIZAIRE on 13/05/2022. */
class TotalSalesViewModel(private val repository: TotalSalesRepository):ViewModel(){

    fun getTotalSales(): LiveData<MutableList<TotalSales>> {
        val mutabledata = MutableLiveData<MutableList<TotalSales>>()
        repository.getTotalSale().observeForever {
            mutabledata.value = it
        }
        return mutabledata
    }
}