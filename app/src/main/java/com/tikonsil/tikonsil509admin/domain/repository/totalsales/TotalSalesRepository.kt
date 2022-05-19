package com.tikonsil.tikonsil509admin.domain.repository.totalsales

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.tikonsil.tikonsil509admin.data.remote.provider.TotalSalesProvider
import com.tikonsil.tikonsil509admin.domain.model.TotalSales
import com.tikonsil.tikonsil509admin.domain.model.TotalUser

/** * Created by ISMOY BELIZAIRE on 13/05/2022. */
class TotalSalesRepository {
 private val totalSalesProvider by lazy { TotalSalesProvider() }
     fun getTotalSale():LiveData<MutableList<TotalSales>>{
         val mutableLiveData = MutableLiveData<MutableList<TotalSales>>()
         totalSalesProvider.getTotalSales()?.addValueEventListener(object : ValueEventListener {
             val listdata = mutableListOf<TotalSales>()
             override fun onDataChange(snapshot: DataSnapshot) {
                 if (snapshot.exists()){
                     for (ds in snapshot.children){
                         val listtotal = TotalSales(snapshot.childrenCount.toInt())
                         listdata.add(listtotal)
                     }
                 }
                 mutableLiveData.value =listdata
             }

             override fun onCancelled(error: DatabaseError) {
                 TODO("Not yet implemented")
             }

         })
         return mutableLiveData
     }

}