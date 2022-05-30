package com.tikonsil.tikonsil509admin.domain.repository.listnotification

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.tikonsil.tikonsil509admin.data.remote.provider.NotificationProvider
import com.tikonsil.tikonsil509admin.domain.model.NotificationList

/** * Created by ISMOY BELIZAIRE on 16/05/2022. */
class ListNotificationRepository {
    private val notificationprovider by lazy { NotificationProvider() }

    suspend fun getListNotification(): LiveData<MutableList<NotificationList>> {
        val mutableLiveDat = MutableLiveData<MutableList<NotificationList>>()
        notificationprovider.getNotification()?.addListenerForSingleValueEvent(object : ValueEventListener {
            val listnotificationdata = mutableListOf<NotificationList>()
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (ds in snapshot.children){
                        val title = ds.child("title").value.toString()
                        val message =ds.child("message").value.toString()
                        val idUser = ds.child("iduser").value.toString()
                        val key = ds.key.toString()
                        val date =ds.child("date").value.toString()
                        val phone =ds.child("phone").value.toString()
                        val listnotification = NotificationList(title,message,idUser,key,date,phone)
                        listnotificationdata.add(listnotification)
                    }
                    mutableLiveDat.value =listnotificationdata
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
        return mutableLiveDat
    }

}