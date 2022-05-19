package com.tikonsil.tikonsil509admin.presentation.listnotification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tikonsil.tikonsil509admin.domain.repository.listnotification.ListNotificationRepository

/** * Created by ISMOY BELIZAIRE on 16/05/2022. */
class ListNotificationViewModelProvider(private val repository: ListNotificationRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListNotificationViewModel(repository) as T
    }
}