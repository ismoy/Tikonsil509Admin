package com.tikonsil.tikonsil509admin.domain.repository.register

import android.app.Application
import com.tikonsil.tikonsil509admin.data.remote.provider.firebaseApi.FirebaseApi
import com.tikonsil.tikonsil509admin.data.remote.retrofitInstance.RetrofitInstance
import com.tikonsil.tikonsil509admin.domain.model.Users
import retrofit2.Response

/** * Created by ISMOY BELIZAIRE on 23/04/2022. */
class RegisterRepository(private var application: Application) {

    init {
        this.application = application
    }
    suspend fun register(uiduser: String, users: Users): Response<Users> {
        val _tikonsilApi = RetrofitInstance(FirebaseApi.getFSApis().base_url_firebase_instance).tikonsilApi
        return _tikonsilApi.registerClient(uiduser,users)
    }
}