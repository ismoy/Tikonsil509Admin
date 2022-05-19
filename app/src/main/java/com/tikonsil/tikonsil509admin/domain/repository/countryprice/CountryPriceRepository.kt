package com.tikonsil.tikonsil509admin.domain.repository.countryprice

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.tikonsil.tikonsil509admin.domain.model.CountryPrice
import com.tikonsil.tikonsil509admin.data.remote.provider.PriceCountryProvider

/** * Created by ISMOY BELIZAIRE on 15/05/2022. */
class CountryPriceRepository {
    private val countrypriceprovider by lazy { PriceCountryProvider() }

    suspend fun getCountryPrice(): LiveData<MutableList<CountryPrice>> {
        val mutableLiveData = MutableLiveData<MutableList<CountryPrice>>()
        countrypriceprovider.getCountryPrice()?.addValueEventListener(object : ValueEventListener {
            val listcountrypricedata = mutableListOf<CountryPrice>()
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val pricetopuphaiti =
                        snapshot.child("pricetopuphaiti").value.toString().toDouble()
                    val pricemoncashhaiti =
                        snapshot.child("pricemoncashhaiti").value.toString().toDouble()
                    val pricecuba = snapshot.child("pricecuba").value.toString().toDouble()
                    val pricemexico = snapshot.child("pricemexico").value.toString().toDouble()
                    val pricepanama = snapshot.child("pricepanama").value.toString().toDouble()
                    val priceRD = snapshot.child("priceRD").value.toString().toDouble()
                    val pricebrasil = snapshot.child("pricebrasil").value.toString().toDouble()
                    val pricechile = snapshot.child("pricechile").value.toString().toDouble()
                    val priceus = snapshot.child("priceus").value.toString().toDouble()
                    val pricelapoulahaiti =
                        snapshot.child("pricelapoulahaiti").value.toString().toDouble()
                    val pricenatcashhaiti =
                        snapshot.child("pricenatcashhaiti").value.toString().toDouble()
                    val listprice = CountryPrice(
                        pricetopuphaiti,
                        pricemoncashhaiti,
                        pricecuba,
                        pricemexico,
                        pricepanama,
                        priceRD,
                        pricebrasil,
                        pricechile,
                        priceus,
                        pricelapoulahaiti,
                        pricenatcashhaiti
                    )
                    listcountrypricedata.add(listprice)

                    mutableLiveData.value = listcountrypricedata
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
        return mutableLiveData
    }
}