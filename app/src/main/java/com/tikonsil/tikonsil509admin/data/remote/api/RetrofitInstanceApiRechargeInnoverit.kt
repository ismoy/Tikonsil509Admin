package com.tikonsil.tikonsil509admin.data.remote.api

import com.tikonsil.tikonsil509admin.utils.Constant.Companion.BASE_URL_INNOVIT
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/** * Created by ISMOY BELIZAIRE on 01/01/2023. */
object RetrofitInstanceApiRechargeInnoverit {

 private val retrofit by lazy {
  Retrofit.Builder()
   .baseUrl(BASE_URL_INNOVIT)
   .addConverterFactory(GsonConverterFactory.create())
   .build()
 }
 val tikonsilApi:TikonsilApi by lazy {
  retrofit.create(TikonsilApi::class.java)
 }
}