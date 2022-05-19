package com.tikonsil.tikonsil509admin.domain.repository.registrereduser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.tikonsil.tikonsil509admin.data.remote.provider.RegistreredUserProvider
import com.tikonsil.tikonsil509admin.domain.model.RegisteredUser

/** * Created by ISMOY BELIZAIRE on 13/05/2022. */
class RegistreredUserRepository {
 private  val registrereduserprovider by lazy { RegistreredUserProvider() }
 suspend fun getRegistreredUsers(): LiveData<MutableList<RegisteredUser>> {
  val mutableLiveData = MutableLiveData<MutableList<RegisteredUser>>()
  registrereduserprovider.getRegistreredUser()?.addValueEventListener(object :ValueEventListener{
   override fun onDataChange(snapshot: DataSnapshot) {
    val listdata = mutableListOf<RegisteredUser>()
    if(snapshot.exists()){
     for(ds in snapshot.children){
      val id = ds.child("id").value.toString()
      val firstname = ds.child("firstname").value.toString()
      val lastname = ds.child("lastname").value.toString()
      val email = ds.child("email").value.toString()
      val password = ds.child("password").value.toString()
      val status = ds.child("status").value.toString()
      val phone =ds.child("phone").value.toString()
      val role = ds.child("role").value.toString()
      val balancemoncash = ds.child("soldmoncash").value.toString()
      val balancetopup = ds.child("soldtopup").value.toString()
      val codecountry = ds.child("countrycode").value.toString()
      val balancenatcash = ds.child("soldnatcash").value.toString()
      val balancenlapoula = ds.child("soldlapoula").value.toString()


      val listuser =RegisteredUser(id,firstname, lastname, email, phone,role.toInt(),password,status.toInt(),balancemoncash.toDouble(),balancetopup.toDouble(),balancenatcash.toDouble(),balancenlapoula.toDouble(), codecountry,)
      listdata.add(listuser)
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