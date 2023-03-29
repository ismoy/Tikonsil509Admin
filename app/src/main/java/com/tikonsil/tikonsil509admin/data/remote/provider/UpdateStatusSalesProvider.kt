package com.tikonsil.tikonsil509admin.data.remote.provider

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateStatusSalesProvider {

    var mDatabase: DatabaseReference?= FirebaseDatabase.getInstance().reference.child("Sales")
    var mDatabaseErrorSales: DatabaseReference?= FirebaseDatabase.getInstance().reference.child("SalesError")

    fun updateStatus(idKeyStatus:String,status: String?): Task<Void?> {
        val map: MutableMap<String?, Any?> = HashMap()
        map["status"] = status
        return idKeyStatus.let { mDatabase?.child(it)!!.updateChildren(map) }
    }

    fun updateStatusErrorSales(idKeyStatus:String,status: String?): Task<Void?> {
        val map: MutableMap<String?, Any?> = HashMap()
        map["status"] = status
        return idKeyStatus.let { mDatabaseErrorSales?.child(it)!!.updateChildren(map) }
    }
}