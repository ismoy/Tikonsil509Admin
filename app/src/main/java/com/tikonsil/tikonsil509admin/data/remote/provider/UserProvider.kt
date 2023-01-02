package com.tikonsil.tikonsil509admin.data.remote.provider

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserProvider {

    var mDatabase: DatabaseReference?= FirebaseDatabase.getInstance().reference.child("Admin")

    fun getUser(): DatabaseReference? {
        return mDatabase
    }
}