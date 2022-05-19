package com.tikonsil.tikonsil509admin.presentation.totalnotification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tikonsil.tikonsil509admin.domain.repository.totalnotification.NotificationCountRepository

/** * Created by ISMOY BELIZAIRE on 16/05/2022. */
class NotificationCountViewModeProvider (private val repository: NotificationCountRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotificationCountViewModel(repository) as T
    }
}