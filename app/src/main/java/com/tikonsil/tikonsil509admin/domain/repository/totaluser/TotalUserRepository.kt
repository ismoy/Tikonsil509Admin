package com.tikonsil.tikonsil509admin.domain.repository.totaluser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.tikonsil.tikonsil509.domain.model.LastSales
import com.tikonsil.tikonsil509.domain.model.Users
import com.tikonsil.tikonsil509admin.data.remote.api.RetrofitInstance
import com.tikonsil.tikonsil509admin.data.remote.api.TikonsilApi
import com.tikonsil.tikonsil509admin.data.remote.provider.TotalUserProvider
import com.tikonsil.tikonsil509admin.domain.model.TotalUser
import retrofit2.Response

/** * Created by ISMOY BELIZAIRE on 13/05/2022. */
class TotalUserRepository {
 private val totalUserProvider by lazy { TotalUserProvider() }
 fun getTotalUser(): LiveData<MutableList<TotalUser>> {
  val mutableLiveData = MutableLiveData<MutableList<TotalUser>>()
  totalUserProvider.getTotalUser()?.addValueEventListener(object :ValueEventListener{
   val listdata = mutableListOf<TotalUser>()
   override fun onDataChange(snapshot: DataSnapshot) {
    if (snapshot.exists()){
     for (ds in snapshot.children){
      val listtotal = TotalUser(snapshot.childrenCount.toInt())
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