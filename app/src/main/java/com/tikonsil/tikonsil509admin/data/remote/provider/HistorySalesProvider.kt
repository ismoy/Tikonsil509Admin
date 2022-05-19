package com.tikonsil.tikonsil509admin.data.remote.provider

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/** * Created by ISMOY BELIZAIRE on 14/05/2022. */
class HistorySalesProvider {
 var mDatabase: DatabaseReference?= FirebaseDatabase.getInstance().reference.child("Sales")

 fun getHistorySales(): DatabaseReference? {
  return mDatabase
 }
}