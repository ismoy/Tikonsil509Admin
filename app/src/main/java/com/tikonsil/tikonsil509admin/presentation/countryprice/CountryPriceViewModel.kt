package com.tikonsil.tikonsil509admin.presentation.countryprice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tikonsil.tikonsil509admin.domain.model.CountryPrice
import com.tikonsil.tikonsil509admin.domain.repository.countryprice.CountryPriceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/** * Created by ISMOY BELIZAIRE on 15/05/2022. */
@HiltViewModel
class CountryPriceViewModel @Inject constructor(private val repository: CountryPriceRepository):ViewModel() {

    private val _getCountryPriceResponse:MutableLiveData<MutableList<CountryPrice>> by lazy { MutableLiveData() }

    val getCountryPriceResponse:LiveData<MutableList<CountryPrice>> = _getCountryPriceResponse

    val resultUpdate =repository.resultUpdate
    fun getCountryPrice(){
        viewModelScope.launch {
            repository.getCountryPrice().observeForever{
                _getCountryPriceResponse.value = it
            }
        }
    }

    fun updatePrices(idKey:String,priceMonCash:String,priceLaPouLa:String,priceNatCash:String){
        repository.updatePrices(idKey, priceMonCash, priceLaPouLa, priceNatCash)
    }
}