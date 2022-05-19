package com.tikonsil.tikonsil509admin.presentation.countryprice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tikonsil.tikonsil509admin.domain.model.CountryPrice
import com.tikonsil.tikonsil509admin.domain.repository.countryprice.CountryPriceRepository
import kotlinx.coroutines.launch

/** * Created by ISMOY BELIZAIRE on 15/05/2022. */
class CountryPriceViewModel(private val repository: CountryPriceRepository):ViewModel() {
    fun getCountryPrice():LiveData<MutableList<CountryPrice>>{
        val mutabledata = MutableLiveData <MutableList<CountryPrice>>()
        viewModelScope.launch {
            repository.getCountryPrice().observeForever{
                mutabledata.value = it
            }
        }
        return mutabledata
    }
}