package com.tikonsil.tikonsil509admin.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tikonsil.tikonsil509admin.domain.model.Users
import com.tikonsil.tikonsil509admin.domain.repository.home.UsersRepository
import kotlinx.coroutines.launch
import retrofit2.Response

/** * Created by ISMOY BELIZAIRE on 27/04/2022. */
class UserViewModel(val repository: UsersRepository):ViewModel() {
 val ResposeUsers:MutableLiveData<Response<Users>> = MutableLiveData()

 fun getOnlyUser(uidUser:String){
  viewModelScope.launch {
   val response = repository.getOnlyUser(uidUser)
   ResposeUsers.value = response
  }
 }

}