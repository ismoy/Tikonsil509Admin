package com.tikonsil.tikonsil509admin.domain.repository.home

import com.tikonsil.tikonsil509admin.data.remote.provider.firebaseApi.FirebaseApi
import com.tikonsil.tikonsil509admin.data.remote.retrofitInstance.RetrofitInstance
import com.tikonsil.tikonsil509admin.domain.model.Authorization
import com.tikonsil.tikonsil509admin.domain.model.Users
import retrofit2.Response

/** * Created by ISMOY BELIZAIRE on 27/04/2022. */
class UsersRepository {
    suspend fun getOnlyUser(uidUser:String):Response<Users> {
        val _tikonsilApi = RetrofitInstance(FirebaseApi.getFSApis().base_url_firebase_instance).tikonsilApi
    return _tikonsilApi.getOnlyUser(uidUser)
    }

}