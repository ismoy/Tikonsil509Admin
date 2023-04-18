package com.tikonsil.tikonsil509admin.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tikonsil.tikonsil509admin.domain.model.LastSales
import com.tikonsil.tikonsil509admin.domain.model.*
import com.tikonsil.tikonsil509admin.domain.repository.home.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/** * Created by ISMOY BELIZAIRE on 27/04/2022. */

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UsersRepository) : ViewModel() {

    private val _responseUser: MutableLiveData<Response<Users>> by lazy { MutableLiveData() }
    val responseUser: LiveData<Response<Users>> = _responseUser
    private val _getBalanceResponse: MutableLiveData<String> by lazy { MutableLiveData() }
    val getBalanceResponse: LiveData<String> = _getBalanceResponse
    private val _getUsersResponse:MutableLiveData<MutableList<Users>> by lazy { MutableLiveData() }
    val getUsersResponse:LiveData<MutableList<Users>> = _getUsersResponse
    private val _lastSalesResponse:MutableLiveData<MutableList<Sales>> by lazy { MutableLiveData() }
    val lastSalesResponse:LiveData<MutableList<Sales>> = _lastSalesResponse
    var isExistSnapshot = MutableLiveData<Boolean>()
    private val _registerUserResponse:MutableLiveData<Response<Users>> by lazy { MutableLiveData() }
    var registerUserResponse:LiveData<Response<Users>> = _registerUserResponse
    val isLoading:MutableLiveData<Boolean> = MutableLiveData()

    init {
        isExistSnapshot = repository.isExistSnapshot
    }

    fun getOnlyUser(uidUser: String) {
        viewModelScope.launch {
            val response = repository.getOnlyUser(uidUser)
            _responseUser.value = response
        }
    }

    fun getBalance() {
        viewModelScope.launch {
            repository.getBalance().enqueue(object : Callback<BalanceResponse> {
                override fun onResponse(
                    call: Call<BalanceResponse>,
                    response: Response<BalanceResponse>
                ) {
                    if (response.isSuccessful) {
                        val balance = response.body()?.balance
                        _getBalanceResponse.value = balance.toString()
                    } else {
                        _getBalanceResponse.value = "No tienes permiso por favor comunicate con Innoverit el Error es: ${response.code()}"

                    }
                }

                override fun onFailure(call: Call<BalanceResponse>, t: Throwable) {

                }

            })

        }

    }

    fun getUsers(){
        viewModelScope.launch{
           repository.getUsers().observeForever {
               _getUsersResponse.value = it
           }

        }
    }

    fun getLastSales(): LiveData<MutableList<Sales>> {
        viewModelScope.launch {
            repository.getLastSales().observeForever{
                _lastSalesResponse.value = it
            }
        }
        return _lastSalesResponse
    }

    fun register(uidUser:String,users: Users){
        isLoading.value = true
        viewModelScope.launch {
            val response = repository.register(uidUser,users)
            _registerUserResponse.value = response
        }
    }
}