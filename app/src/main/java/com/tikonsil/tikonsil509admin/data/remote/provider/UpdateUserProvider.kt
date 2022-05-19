package com.tikonsil.tikonsil509admin.data.remote.provider

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.tikonsil.tikonsil509admin.domain.model.RegisteredUser
import java.util.HashMap

/** * Created by ISMOY BELIZAIRE on 27/04/2022. */
class UpdateUserProvider {
 var mDatabase:DatabaseReference?=FirebaseDatabase.getInstance().reference.child("Clients")

 fun update(user: RegisteredUser?): Task<Void>? {
  val map: MutableMap<String?, Any?> = HashMap()
  map.apply {
   put("firstname", user?.firstname)
   put("lastname", user?.lastname)
   put("email", user?.email)
   put("phone", user?.phone)
   put("soldmoncash", user?.soldmoncash)
   put("soldtopup", user?.soldtopup)
   put("role", user?.role)
   put("status", user?.status)
   put("password", user?.password)
  }
  return user?.id?.let {
   mDatabase?.child(it)?.updateChildren(map) }
 }

}