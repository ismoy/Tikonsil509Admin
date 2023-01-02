package com.tikonsil.tikonsil509admin.presentation.costInnoverit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tikonsil.tikonsil509admin.domain.model.CostInnoverit
import com.tikonsil.tikonsil509admin.domain.repository.costInnoverit.PriceCostInnoveritRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class PriceCostInnoveritViewModel:ViewModel() {
    private val repository:PriceCostInnoveritRepository= PriceCostInnoveritRepository()
    val responsePriceCost by lazy { MutableLiveData<Response<CostInnoverit>>() }
    fun registerPriceCost(innoverit: CostInnoverit){
        viewModelScope.launch {
            val response =repository.registerPriceCost(innoverit)
            responsePriceCost.value =response
        }
    }
}