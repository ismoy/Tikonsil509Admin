package com.tikonsil.tikonsil509admin.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tikonsil.tikonsil509admin.domain.model.Users
import com.tikonsil.tikonsil509admin.domain.repository.profile.ProfileRepository
import kotlinx.coroutines.launch
import retrofit2.Response

/** * Created by ISMOY BELIZAIRE on 27/04/2022. */
class ProfileViewModel(val repository: ProfileRepository):ViewModel() {
 val ResposeUsers:MutableLiveData<Response<Users>> = MutableLiveData()

 fun getOnlyUser(uidUser:String){
  viewModelScope.launch {
   val response = repository.getOnlyUser(uidUser)
   ResposeUsers.value = response
  }
 }
}