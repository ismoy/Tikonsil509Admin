package com.tikonsil.tikonsil509admin.presentation.registereduser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tikonsil.tikonsil509admin.domain.model.RegisteredUser
import com.tikonsil.tikonsil509admin.domain.model.TotalUser
import com.tikonsil.tikonsil509admin.domain.repository.registrereduser.RegistreredUserRepository
import kotlinx.coroutines.launch
import retrofit2.Response

/** * Created by ISMOY BELIZAIRE on 13/05/2022. */
class RegisteredUserViewModel(private val repository: RegistreredUserRepository):ViewModel() {

    fun getRegistreredUsers():LiveData<MutableList<RegisteredUser>>{
        val mutabledata =MutableLiveData<MutableList<RegisteredUser>>()
        viewModelScope.launch {
            repository.getRegistreredUsers().observeForever{
                mutabledata.value =it
            }
        }
        return mutabledata
    }
}