package com.tikonsil.tikonsil509admin.data.remote.provider

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/** * Created by ISMOY BELIZAIRE on 13/05/2022. */
  class TotalSalesProvider {
    var mDatabase: DatabaseReference?= FirebaseDatabase.getInstance().reference.child("Sales")
    fun getTotalSales(): DatabaseReference? {
        return mDatabase
    }
}