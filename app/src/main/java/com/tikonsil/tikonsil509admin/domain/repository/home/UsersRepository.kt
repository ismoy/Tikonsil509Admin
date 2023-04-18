package com.tikonsil.tikonsil509admin.domain.repository.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.tikonsil.tikonsil509admin.data.remote.provider.TotalSalesProvider
import com.tikonsil.tikonsil509admin.data.remote.provider.TotalUserProvider
import com.tikonsil.tikonsil509admin.data.remote.provider.firebaseApi.FirebaseApi
import com.tikonsil.tikonsil509admin.data.remote.retrofitInstance.Headers
import com.tikonsil.tikonsil509admin.data.remote.retrofitInstance.RetrofitInstance
import com.tikonsil.tikonsil509admin.domain.model.BalanceResponse
import com.tikonsil.tikonsil509admin.domain.model.Sales
import com.tikonsil.tikonsil509admin.domain.model.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

/** * Created by ISMOY BELIZAIRE on 27/04/2022. */
class UsersRepository @Inject constructor(private val totalUserProvider: TotalUserProvider,private val totalSalesProvider: TotalSalesProvider){
    var isExistSnapshot = MutableLiveData<Boolean>()

    suspend fun getOnlyUser(uidUser: String): Response<Users> {
        val _tikonsilApi =
            RetrofitInstance(FirebaseApi.getFSApis().base_url_firebase_instance).tikonsilApi
        return _tikonsilApi.getOnlyUser(uidUser)
    }

    suspend fun getBalance(): Call<BalanceResponse> {
        val _tikonsilApi = RetrofitInstance(FirebaseApi.getFSApis().base_url_tikonsil).tikonsilApi
        val response = withContext(Dispatchers.IO) {
            _tikonsilApi.getBalance(
                FirebaseApi.getFSApis().end_point_getBalance,
                Headers.getHeaderTikonsil509()
            )
        }
        return response
    }

    suspend fun register(uiduser: String, users: Users): Response<Users> {
        val _tikonsilApi =
            RetrofitInstance(FirebaseApi.getFSApis().base_url_firebase_instance).tikonsilApi
        return _tikonsilApi.registerClient(uiduser, users)
    }

    fun getUsers(): LiveData<MutableList<Users>> {
        val mutableLiveData = MutableLiveData<MutableList<Users>>()
        totalUserProvider.getTotalUser()?.addValueEventListener(object : ValueEventListener {
            val listdata = mutableListOf<Users>()
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (ds in snapshot.children) {
                        val value = ds.getValue(Users::class.java)

                        if (value?.status == 1) {
                            listdata.add(value)
                        }

                    }
                }
                mutableLiveData.value = listdata
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
        return mutableLiveData
    }

    fun getLastSales(): LiveData<MutableList<Sales>> {
        val mutableLiveData = MutableLiveData<MutableList<Sales>>()
        totalSalesProvider.getLastSales()?.addValueEventListener(object : ValueEventListener {
            val listlastsalesdata = mutableListOf<Sales>()
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (ds in snapshot.children) {
                        val value = ds.getValue(Sales::class.java)
                        if (value != null) {
                            listlastsalesdata.add(value)
                        }
                    }
                    mutableLiveData.value = listlastsalesdata
                } else {
                    isExistSnapshot.value = false
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
        return mutableLiveData
    }

}