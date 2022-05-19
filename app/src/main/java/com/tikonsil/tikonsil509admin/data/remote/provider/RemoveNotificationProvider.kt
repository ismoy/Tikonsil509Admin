package com.tikonsil.tikonsil509admin.data.remote.provider

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/** * Created by ISMOY BELIZAIRE on 16/05/2022. */
class RemoveNotificationProvider {
    var mDatabase: DatabaseReference?= FirebaseDatabase.getInstance().reference.child("Notification")
    fun delete(key: String?) {
         mDatabase!!.child(key!!).removeValue()
    }

}