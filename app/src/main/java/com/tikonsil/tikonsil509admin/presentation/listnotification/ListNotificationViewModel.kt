package com.tikonsil.tikonsil509admin.presentation.listnotification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tikonsil.tikonsil509admin.domain.model.NotificationList
import com.tikonsil.tikonsil509admin.domain.repository.listnotification.ListNotificationRepository
import kotlinx.coroutines.launch

/** * Created by ISMOY BELIZAIRE on 16/05/2022. */
class ListNotificationViewModel(private val repository: ListNotificationRepository):ViewModel() {
    fun getListNotification(): LiveData<MutableList<NotificationList>> {
        val mutabledata = MutableLiveData<MutableList<NotificationList>>()
        viewModelScope.launch {
            repository.getListNotification().observeForever{
                mutabledata.value = it
            }
        }
        return mutabledata
    }
}