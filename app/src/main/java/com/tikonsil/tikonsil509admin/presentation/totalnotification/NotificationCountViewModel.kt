package com.tikonsil.tikonsil509admin.presentation.totalnotification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tikonsil.tikonsil509admin.domain.model.NotificationCount
import com.tikonsil.tikonsil509admin.domain.repository.totalnotification.NotificationCountRepository

/** * Created by ISMOY BELIZAIRE on 16/05/2022. */
class NotificationCountViewModel(private val repository: NotificationCountRepository):ViewModel() {
    fun getNotificationCount(): LiveData<MutableList<NotificationCount>> {
        val mutabledata = MutableLiveData<MutableList<NotificationCount>>()
        repository.getNotificationCount().observeForever {
            mutabledata.value = it
        }
        return mutabledata
    }
}