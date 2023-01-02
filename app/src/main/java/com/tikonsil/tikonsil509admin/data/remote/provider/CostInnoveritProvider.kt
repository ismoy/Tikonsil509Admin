package com.tikonsil.tikonsil509admin.data.remote.provider

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.tikonsil.tikonsil509admin.domain.model.CostInnoverit
import com.tikonsil.tikonsil509admin.domain.model.IdProducts

class CostInnoveritProvider {

    var mDatabase: DatabaseReference?= FirebaseDatabase.getInstance().reference.child("PriceCountryInnoverit")

    fun create(costInnoverit: CostInnoverit): Task<Void>? {
        return mDatabase?.push()?.setValue(costInnoverit)
    }

    fun getKey(): String? {
        return mDatabase?.ref?.push()?.key
    }

    fun getIdKeyAdded(id_product:String): Query? {
        return mDatabase?.orderByChild("id_product")?.equalTo(id_product)
    }
}