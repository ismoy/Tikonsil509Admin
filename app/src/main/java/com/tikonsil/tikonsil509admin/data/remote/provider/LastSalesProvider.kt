package com.tikonsil.tikonsil509admin.data.remote.provider

import androidx.compose.runtime.key
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.tikonsil.tikonsil509.domain.model.Sales

/** * Created by ISMOY BELIZAIRE on 01/05/2022. */
class LastSalesProvider {
 var mDatabase:DatabaseReference?= FirebaseDatabase.getInstance().reference.child("Sales")
 suspend fun getLastSales(): Query? {
  return mDatabase?.ref?.limitToLast(11)
 }

}