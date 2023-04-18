package com.tikonsil.tikonsil509admin.domain.repository.costInnoverit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.tikonsil.tikonsil509admin.data.local.dao.CostInnoveritDao
import com.tikonsil.tikonsil509admin.data.local.entity.CostInnoveritEntity
import com.tikonsil.tikonsil509admin.data.local.entity.SalesEntity
import com.tikonsil.tikonsil509admin.data.remote.provider.CostInnoveritProvider
import com.tikonsil.tikonsil509admin.data.remote.provider.firebaseApi.FirebaseApi
import com.tikonsil.tikonsil509admin.data.remote.retrofitInstance.RetrofitInstance
import com.tikonsil.tikonsil509admin.domain.model.CostInnoverit
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class PriceCostInnoveritRepository @Inject constructor(private val costInnoveritDao: CostInnoveritDao) {

    private val costInnoveritProvider by lazy { CostInnoveritProvider() }



    suspend fun insertCost(costInnoverit: List<CostInnoveritEntity>){
        costInnoveritDao.insertData(costInnoverit)
    }

    suspend fun deleteAllCost(){
        costInnoveritDao.deleteAll()
    }

    fun readCost(): Flow<List<CostInnoveritEntity>> {
        return costInnoveritDao.readData()
    }

    fun searchDataBase(searchQuery:String):Flow<List<CostInnoveritEntity>>{
        return costInnoveritDao.searchDataBase(searchQuery)
    }
    suspend fun registerPriceCost(innoverit: CostInnoverit):Response<CostInnoverit>{
        val _tikonsilApi = RetrofitInstance(FirebaseApi.getFSApis().base_url_firebase_instance).tikonsilApi
        return _tikonsilApi.registerCostTotal(FirebaseApi.getFSApis().end_point_add_product,innoverit)
    }

    fun getListProduct(): LiveData<MutableList<CostInnoverit>> {
        val mutableLiveDat = MutableLiveData<MutableList<CostInnoverit>>()
        costInnoveritProvider.getListProduct().addListenerForSingleValueEvent(object :ValueEventListener{
            val listData = mutableListOf<CostInnoverit>()
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (ds in snapshot.children){
                        val priceReceiver = ds.child("priceReceiver").value.toString()
                        val operatorName =ds.child("operatorName").value.toString()
                        val priceSales=ds.child("priceSales").value.toString()
                        val nameMoneyCountryReceiver=ds.child("nameMoneyCountryReceiver").value.toString()
                        val nameMoneyCountrySale=ds.child("nameMoneyCountrySale").value.toString()
                        val idProduct=ds.child("idProduct").value.toString()
                        val country=ds.child("country").value.toString()
                        val formatPrice=ds.child("formatPrice").value.toString()
                        val idKey=ds.key.toString()
                        val value =CostInnoverit(priceReceiver, operatorName, priceSales, nameMoneyCountryReceiver, nameMoneyCountrySale, idProduct, country, formatPrice, idKey)
                        listData.add(value)
                    }
                    mutableLiveDat.value = listData
                }
            }

            override fun onCancelled(error: DatabaseError) {}

        })
        return mutableLiveDat
    }
}