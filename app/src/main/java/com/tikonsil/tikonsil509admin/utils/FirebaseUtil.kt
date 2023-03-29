package com.tikonsil.tikonsil509admin.utils

import com.google.firebase.database.FirebaseDatabase
import com.tikonsil.tikonsil509admin.data.remote.provider.firebaseApi.Apis
import com.tikonsil.tikonsil509admin.utils.component.await

class FirebaseUtil {

    private var mDatabase = FirebaseDatabase.getInstance()


    suspend fun getApis(): Apis?{
        return try{
            val data = mDatabase
                .reference
                .child("Tikonsil509")
                .get()
                .await()
            data?.getValue(Apis::class.java)
        }catch (e : Exception){
            null
        }
    }
}