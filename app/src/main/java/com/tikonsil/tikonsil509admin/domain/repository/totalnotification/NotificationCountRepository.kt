package com.tikonsil.tikonsil509admin.domain.repository.totalnotification

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.tikonsil.tikonsil509admin.data.remote.provider.NotificationCountProvider
import com.tikonsil.tikonsil509admin.domain.model.NotificationCount

/** * Created by ISMOY BELIZAIRE on 16/05/2022. */
class NotificationCountRepository {
    private val totalnotificationcount by lazy { NotificationCountProvider() }
    fun getNotificationCount(): LiveData<MutableList<NotificationCount>> {
        val mutableLiveData = MutableLiveData<MutableList<NotificationCount>>()
        totalnotificationcount.getNotificationCount()?.addValueEventListener(object : ValueEventListener {
            val listdata = mutableListOf<NotificationCount>()
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (ds in snapshot.children){
                        val listtotal = NotificationCount(snapshot.childrenCount.toInt())
                        listdata.add(listtotal)
                    }
                }
                mutableLiveData.value =listdata
                Log.d("VALORSIZE",listdata.size.toString())
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
        return mutableLiveData
    }
}