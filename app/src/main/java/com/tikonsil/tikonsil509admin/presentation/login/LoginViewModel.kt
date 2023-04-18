package com.tikonsil.tikonsil509admin.presentation.login

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.tikonsil.tikonsil509admin.domain.repository.login.LoginRepository
import kotlinx.coroutines.launch

/** * Created by ISMOY BELIZAIRE on 26/04/2022. */
class LoginViewModel(private var repository: LoginRepository):ViewModel() {

 private val _responseLogin:MutableLiveData<Task<AuthResult>> = MutableLiveData()
 val responseLogin:LiveData<Task<AuthResult>> = _responseLogin
 val loading by lazy { MutableLiveData<Boolean>() }

 @SuppressLint("NullSafeMutableLiveData")
 fun login(email:String,password:String){
  loading.value = true
  viewModelScope.launch {
   val response = repository.login(email, password)
    _responseLogin.value = response
  }
 }
}