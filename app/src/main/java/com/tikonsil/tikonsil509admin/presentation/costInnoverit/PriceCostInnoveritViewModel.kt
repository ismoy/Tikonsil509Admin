package com.tikonsil.tikonsil509admin.presentation.costInnoverit

import androidx.lifecycle.*
import com.tikonsil.tikonsil509admin.data.local.entity.CostInnoveritEntity
import com.tikonsil.tikonsil509admin.data.local.entity.SalesEntity
import com.tikonsil.tikonsil509admin.domain.model.CostInnoverit
import com.tikonsil.tikonsil509admin.domain.repository.costInnoverit.PriceCostInnoveritRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PriceCostInnoveritViewModel @Inject constructor(private val repository: PriceCostInnoveritRepository):ViewModel() {
    val responsePriceCost by lazy { MutableLiveData<Response<CostInnoverit>>() }
    private val _lisIdProduct:MutableLiveData<MutableList<CostInnoverit>> by lazy { MutableLiveData() }
    val listIdProduct:LiveData<MutableList<CostInnoverit>> = _lisIdProduct


    val readAllData = repository.readCost().asLiveData()

    fun  insertCost(costInnoverit: List<CostInnoveritEntity>){
        viewModelScope.launch {
            repository.insertCost(costInnoverit)
        }
    }

    fun deleteAllCost(){
        viewModelScope.launch {
            repository.deleteAllCost()
        }
    }

    fun searchDataBase(searchQuery:String):LiveData<List<CostInnoveritEntity>>{
        return repository.searchDataBase(searchQuery).asLiveData()
    }
    fun registerPriceCost(innoverit: CostInnoverit){
        viewModelScope.launch {
            val response =repository.registerPriceCost(innoverit)
            responsePriceCost.value =response
        }
    }

    fun getListProduct(){
        viewModelScope.launch {
            repository.getListProduct().observeForever {
              _lisIdProduct.value = it
            }
        }
    }
}