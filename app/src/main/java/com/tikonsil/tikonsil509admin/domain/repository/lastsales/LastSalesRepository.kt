package com.tikonsil.tikonsil509admin.domain.repository.lastsales

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.tikonsil.tikonsil509.domain.model.LastSales
import com.tikonsil.tikonsil509admin.data.remote.provider.LastSalesProvider

/** * Created by ISMOY BELIZAIRE on 28/04/2022. */
class LastSalesRepository {

 private  val salesProvider = LastSalesProvider()

 var isExistSnapshot= MutableLiveData<Boolean>()

 suspend fun getLastSales():LiveData<MutableList<LastSales>>{
  val mutableLiveData =MutableLiveData<MutableList<LastSales>>()
  salesProvider.getLastSales()?.addValueEventListener(object :ValueEventListener{
   val listlastsalesdata = mutableListOf<LastSales>()
   override fun onDataChange(snapshot: DataSnapshot) {
    if (snapshot.exists()){
     for (ds in snapshot.children){
      val subtotal = ds.child("subtotal").value.toString()
      val date = ds.child("date").value.toString()
      val codecountry = ds.child("codecountry").value.toString()
      val typerecharge = ds.child("typerecharge").value.toString()
      val salePrice =ds.child("salesPrice").value.toString()
      val listlastsales = LastSales(subtotal,date,typerecharge, codecountry,salePrice)
      listlastsalesdata.add(listlastsales)
     }
     mutableLiveData.value =listlastsalesdata
    }else{
     isExistSnapshot.value =true
    }
   }

   override fun onCancelled(error: DatabaseError) {
   }

  })
   return mutableLiveData
 }
}