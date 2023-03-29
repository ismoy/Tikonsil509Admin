package com.tikonsil.tikonsil509admin.data.remote.provider

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

 class ErrorSalesProvider {
    var mDatabase: DatabaseReference?= FirebaseDatabase.getInstance().reference.child("SalesError")

     fun getSalesError():DatabaseReference?{
        return mDatabase
    }

}