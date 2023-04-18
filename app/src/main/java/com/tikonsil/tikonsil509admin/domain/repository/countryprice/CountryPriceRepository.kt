package com.tikonsil.tikonsil509admin.domain.repository.countryprice

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.tikonsil.tikonsil509admin.domain.model.CountryPrice
import com.tikonsil.tikonsil509admin.data.remote.provider.PriceCountryProvider
import javax.inject.Inject

/** * Created by ISMOY BELIZAIRE on 15/05/2022. */
class CountryPriceRepository @Inject constructor(private val countrypriceprovider:PriceCountryProvider) {

    val resultUpdate:MutableLiveData<Task<Void>> by lazy { MutableLiveData() }
    fun getCountryPrice(): LiveData<MutableList<CountryPrice>> {
        val mutableLiveData = MutableLiveData<MutableList<CountryPrice>>()
        countrypriceprovider.getCountryPrice()?.addValueEventListener(object : ValueEventListener {
            val listcountrypricedata = mutableListOf<CountryPrice>()
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    for (ds in snapshot.children){
                        val value = ds.getValue(CountryPrice::class.java)
                        if (value != null) {
                            value.idKey = ds.key
                            listcountrypricedata.add(value)
                        }
                    }
                    mutableLiveData.value = listcountrypricedata
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
        return mutableLiveData
    }

    fun updatePrices(idKey:String,priceMonCash:String,priceLaPouLa:String,priceNatCash:String){
        countrypriceprovider.updatePrice(idKey, priceMonCash, priceLaPouLa, priceNatCash).addOnCompleteListener {
            resultUpdate.value =it
        }
    }
}