package com.tikonsil.tikonsil509admin.presentation.historysales

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.tikonsil.tikonsil509admin.data.local.entity.SalesEntity
import com.tikonsil.tikonsil509admin.data.local.entity.SalesErrorEntity
import com.tikonsil.tikonsil509admin.domain.model.Sales
import com.tikonsil.tikonsil509admin.domain.repository.historysales.HistorySalesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/** * Created by ISMOY BELIZAIRE on 14/05/2022. */
@HiltViewModel
class HistorySalesViewModel @Inject constructor(private val repository: HistorySalesRepository):ViewModel(){


    var isExistSnapshot = MutableLiveData<Boolean>()
    var isExistSnapshotError = MutableLiveData<Boolean>()
    private val _getErrorSales:MutableLiveData<MutableList<Sales>> by lazy { MutableLiveData() }
    val getErrorSales:LiveData<MutableList<Sales>> =_getErrorSales
    init {
      isExistSnapshot = repository.isExistSnapshot
        isExistSnapshotError = repository.isExistSnapshotError
    }
    val readData = repository.readData().asLiveData()
    val readSalesError = repository.readSalesError().asLiveData()

    fun insertData(sales:List<SalesEntity>){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(sales)
        }
    }

    fun searchDataBase(searchQuery:String):LiveData<List<SalesEntity>>{
        return repository.searchDataBase(searchQuery).asLiveData()
    }

    fun deleteAll(){
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    fun insertSalesError(sales:List<SalesErrorEntity>){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertSalesError(sales)
        }
    }

    fun searchSalesError(searchQuery:String):LiveData<List<SalesErrorEntity>>{
        return repository.searchSalesError(searchQuery).asLiveData()
    }

    fun deleteAllSalesError(){
        viewModelScope.launch {
            repository.deleteAllSalesError()
        }
    }




    fun getHistorySales(): LiveData<List<Sales>> {
        val mutabledata = MutableLiveData<List<Sales>>()
        viewModelScope.launch {
            repository.getHistorySales().observeForever{
                mutabledata.value = it
            }
        }
        return mutabledata
    }

    fun historyErrorSales(){
        viewModelScope.launch {
           repository.getErrorSales().observeForever {
               _getErrorSales.value = it
           }

        }
    }
}