package com.tikonsil.tikonsil509admin.presentation.totaluser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tikonsil.tikonsil509.domain.model.LastSales
import com.tikonsil.tikonsil509.domain.model.Users
import com.tikonsil.tikonsil509admin.domain.model.TotalUser
import com.tikonsil.tikonsil509admin.domain.repository.totaluser.TotalUserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlin.coroutines.coroutineContext

/** * Created by ISMOY BELIZAIRE on 13/05/2022. */
class TotalUserViewModel(private val repository: TotalUserRepository):ViewModel() {

    fun getTotalUser():LiveData<MutableList<TotalUser>>{
        val mutabledata =MutableLiveData<MutableList<TotalUser>>()
            repository.getTotalUser().observeForever {
                mutabledata.value = it
            }
        return mutabledata
    }
}