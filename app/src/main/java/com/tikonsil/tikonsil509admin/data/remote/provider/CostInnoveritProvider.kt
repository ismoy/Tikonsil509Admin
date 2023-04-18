package com.tikonsil.tikonsil509admin.data.remote.provider

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.tikonsil.tikonsil509admin.domain.model.CostInnoverit
import com.tikonsil.tikonsil509admin.domain.model.IdProducts

class CostInnoveritProvider {

    var mDatabase: DatabaseReference= FirebaseDatabase.getInstance().reference.child("IdProductCountryInnoverit")

    fun getListProduct(): DatabaseReference {
        return mDatabase
    }

    fun updateStatus(idKeyStatus:String,priceReceiver: String?,idProduct:Int,priceSales:String,formatPrice:String): Task<Void?> {
        val map: MutableMap<String?, Any?> = HashMap()
        map["priceReceiver"] = priceReceiver
        map["idProduct"] = idProduct
        map["priceSales"] = priceSales
        map["formatPrice"] = formatPrice
        return idKeyStatus.let { mDatabase.child(it).updateChildren(map) }
    }
}