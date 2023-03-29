package com.tikonsil.tikonsil509admin.domain.repository.sendRecharge

import com.tikonsil.tikonsil509admin.data.remote.provider.firebaseApi.FirebaseApi
import com.tikonsil.tikonsil509admin.data.remote.retrofitInstance.Headers
import com.tikonsil.tikonsil509admin.data.remote.retrofitInstance.RetrofitInstance
import com.tikonsil.tikonsil509admin.domain.model.SendRechargeProduct
import com.tikonsil.tikonsil509admin.domain.model.SendRechargeResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call

class SendRechargeRepository {

    suspend fun sendRechargeViaInnoverit(sendRechargeProduct: SendRechargeProduct):Result<Call<SendRechargeResponse>>{
        return runCatching {
            val _tikonsilApi = RetrofitInstance(FirebaseApi.getFSApis().base_url_tikonsil).tikonsilApi
            val response = _tikonsilApi.sendProduct(
                    Headers.getHeaderTikonsil509(),FirebaseApi.getFSApis().end_point_send_product,
                    sendRechargeProduct)

            response
        }
    }
}