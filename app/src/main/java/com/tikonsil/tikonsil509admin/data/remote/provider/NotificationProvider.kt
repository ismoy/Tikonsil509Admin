package com.tikonsil.tikonsil509admin.data.remote.provider

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/** * Created by ISMOY BELIZAIRE on 16/05/2022. */
class NotificationProvider {
    var mDatabase: DatabaseReference?= FirebaseDatabase.getInstance().reference.child("Notification")

    fun getNotification(): DatabaseReference? {
        return mDatabase
    }
}