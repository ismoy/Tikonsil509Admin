package com.tikonsil.tikonsil509admin.domain.repository.costInnoverit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.tikonsil.tikonsil509admin.data.remote.provider.CostInnoveritProvider
import com.tikonsil.tikonsil509admin.data.remote.provider.firebaseApi.FirebaseApi
import com.tikonsil.tikonsil509admin.data.remote.retrofitInstance.RetrofitInstance
import com.tikonsil.tikonsil509admin.domain.model.CostInnoverit
import com.tikonsil.tikonsil509admin.domain.model.Sales
import retrofit2.Response
import kotlin.math.cos

class PriceCostInnoveritRepository {

    private val costInnoveritProvider by lazy { CostInnoveritProvider() }
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
                       val priceReceiver =ds.child("priceReceiver").value.toString()
                       val operatorName =ds.child("operatorName").value.toString()
                       val priceSales =ds.child("priceSales").value.toString()
                        val nameMoneyCountryReceiver =ds.child("nameMoneyCountryReceiver").value.toString()
                        val nameMoneyCountrySale =ds.child("nameMoneyCountrySale").value.toString()
                        val idProduct =ds.child("idProduct").value.toString()
                        val country =ds.child("country").value.toString()
                        val formatPrice =ds.child("formatPrice").value.toString()
                        val idKey = ds.key
                        val listAdded =CostInnoverit(priceReceiver, operatorName, priceSales, nameMoneyCountryReceiver, nameMoneyCountrySale, idProduct, country, formatPrice,idKey.toString())
                        listData.add(listAdded)
                    }
                    mutableLiveDat.value = listData
                }
            }

            override fun onCancelled(error: DatabaseError) {}

        })
        return mutableLiveDat
    }
}