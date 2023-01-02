package com.tikonsil.tikonsil509admin.data.remote.provider

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.tikonsil.tikonsil509admin.domain.model.IdProducts

class IdProductProvider {

    var mDatabase: DatabaseReference?= FirebaseDatabase.getInstance().reference.child("IdProductCountryInnoverit")

    fun createProduct(idProducts: IdProducts): Task<Void>? {
        return mDatabase?.child(idProducts.idCostInnoverit)?.setValue(idProducts)
    }
}