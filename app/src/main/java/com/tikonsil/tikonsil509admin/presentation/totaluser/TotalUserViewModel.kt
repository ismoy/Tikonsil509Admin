package com.tikonsil.tikonsil509admin.presentation.totaluser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tikonsil.tikonsil509admin.domain.model.TotalUser
import com.tikonsil.tikonsil509admin.domain.repository.totaluser.TotalUserRepository

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