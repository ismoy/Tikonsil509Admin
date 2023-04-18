package com.tikonsil.tikonsil509admin.domain.repository.historysales

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.tikonsil.tikonsil509admin.data.local.dao.SalesDao
import com.tikonsil.tikonsil509admin.data.local.dao.SalesErrorDao
import com.tikonsil.tikonsil509admin.data.local.entity.SalesEntity
import com.tikonsil.tikonsil509admin.data.local.entity.SalesErrorEntity
import com.tikonsil.tikonsil509admin.data.remote.provider.ErrorSalesProvider
import com.tikonsil.tikonsil509admin.data.remote.provider.HistorySalesProvider
import com.tikonsil.tikonsil509admin.domain.model.Sales
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/** * Created by ISMOY BELIZAIRE on 14/05/2022. */
class HistorySalesRepository @Inject constructor(private val salesDao: SalesDao,private val salesErrorDao: SalesErrorDao,
                                                 private val historysalesprovider:HistorySalesProvider,private val errorSalesProvider: ErrorSalesProvider) {

    var isExistSnapshot = MutableLiveData<Boolean>()
    var isExistSnapshotError = MutableLiveData<Boolean>()

    fun readData():Flow<List<SalesEntity>>{
        return salesDao.readData()
    }

    suspend fun insertData(sales: List<SalesEntity>){
        salesDao.insertData(sales)
    }

    fun searchDataBase(searchQuery:String):Flow<List<SalesEntity>>{
        return salesDao.searchDataBase(searchQuery)
    }

   suspend fun deleteAll(){
        salesDao.deleteAll()
    }

    fun readSalesError():Flow<List<SalesErrorEntity>>{
        return salesErrorDao.readData()
    }

    suspend fun insertSalesError(sales: List<SalesErrorEntity>){
        salesErrorDao.insertData(sales)
    }

    fun searchSalesError(searchQuery:String):Flow<List<SalesErrorEntity>>{
        return salesErrorDao.searchDataBase(searchQuery)
    }

    suspend fun deleteAllSalesError(){
        salesErrorDao.deleteAll()
    }




     fun getHistorySales(): LiveData<MutableList<Sales>> {
        val mutableLiveDat = MutableLiveData<MutableList<Sales>>()
        historysalesprovider.getHistorySales().addValueEventListener(object : ValueEventListener {
            val listlastdata = mutableListOf<Sales>()
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (ds in snapshot.children){
                        val value = ds.getValue(Sales::class.java)
                        if (value != null) {
                            value.idKey =ds.key
                            listlastdata.add(value)
                        }
                    }
                    mutableLiveDat.value =listlastdata
                }else{
                    isExistSnapshot.value =false
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
        return mutableLiveDat
    }
     fun getErrorSales(): MutableLiveData<MutableList<Sales>> {
        val mutableLiveData = MutableLiveData<MutableList<Sales>>()
        errorSalesProvider.getSalesError()?.addListenerForSingleValueEvent(object :ValueEventListener{
            val listErrorSales = mutableListOf<Sales>()
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (ds in snapshot.children){
                        val value = ds.getValue(Sales::class.java)
                        if (value != null) {
                            listErrorSales.add(value)
                        }
                    }
                    mutableLiveData.value = listErrorSales
                }else{
                    isExistSnapshotError.value = true
                }
            }

            override fun onCancelled(error: DatabaseError) {}

        })
        return mutableLiveData
    }
}