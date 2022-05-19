package com.tikonsil.tikonsil509admin.domain.repository.historysales

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.tikonsil.tikonsil509.domain.model.Sales
import com.tikonsil.tikonsil509admin.data.remote.provider.HistorySalesProvider

/** * Created by ISMOY BELIZAIRE on 14/05/2022. */
class HistorySalesRepository {
    private val historysalesprovider by lazy { HistorySalesProvider() }

    suspend fun getHistorySales(): LiveData<MutableList<Sales>> {
        val mutableLiveDat = MutableLiveData<MutableList<Sales>>()
        historysalesprovider.getHistorySales()?.addValueEventListener(object : ValueEventListener {
            val listlastdata = mutableListOf<Sales>()
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (ds in snapshot.children){
                        val firstname = ds.child("firstname").value.toString()
                        val lastname =ds.child("lastname").value.toString()
                        val phone = ds.child("phone").value.toString()
                        val role =ds.child("role").value.toString()
                        val email =ds.child("email").value.toString()
                        val description = ds.child("description").value.toString()
                        val subtotal = ds.child("subtotal").value.toString()
                        val date = ds.child("date").value.toString()
                        val country = ds.child("country").value.toString()
                        val codecountry =ds.child("codecountry").value.toString()
                        val typerecharge = ds.child("typerecharge").value.toString()
                        val listsales = Sales("",firstname,lastname, email,role.toInt(),typerecharge, phone, date,country,codecountry, subtotal, description)
                        listlastdata.add(listsales)
                    }
                    mutableLiveDat.value =listlastdata
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
        return mutableLiveDat
    }

}