package com.tikonsil.tikonsil509admin.domain.repository.sendRecharge

import com.tikonsil.tikonsil509admin.data.remote.api.TikonsilApi
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
import javax.inject.Inject
import javax.inject.Named

class SendRechargeRepository @Inject constructor( @Named("base_url") private val url:String, private val tikonsilApi: TikonsilApi) {

    suspend fun sendRechargeViaInnoverit(sendRechargeProduct: SendRechargeProduct): Result<Call<SendRechargeResponse>> {
        return runCatching {
            val response = tikonsilApi.sendProduct(
                Headers.getHeaderTikonsil509(),
                "$url${FirebaseApi.getFSApis().end_point_send_product}",
                sendRechargeProduct
            )
            response
        }
    }

}