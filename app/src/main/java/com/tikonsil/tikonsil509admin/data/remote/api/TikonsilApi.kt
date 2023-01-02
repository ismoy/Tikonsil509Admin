package com.tikonsil.tikonsil509admin.data.remote.api

import com.tikonsil.tikonsil509.domain.model.*
import com.tikonsil.tikonsil509admin.domain.model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

/** * Created by ISMOY BELIZAIRE on 23/04/2022. */
interface TikonsilApi {
 @PATCH("Admin/{uidUser}.json")
 suspend fun registerClient(
  @Path("uidUser") uidUser:String,@Body param:Users
 ):Response<Users>

 @GET("Admin/{uidUser}.json")
 suspend fun getOnlyUser(
  @Path("uidUser") uidUser:String):Response<Users>

 suspend fun Sales(@Body sales: Sales):Response<Sales>

@GET("Sales/{uidUser}.json")
 suspend fun getLastSales():Response<List<LastSales>>

 @GET("CountryPrice.json")
 suspend fun getCountryPrice():Response<CountryPrice>

 @GET("Clients/GScaoMbBZ8fbPM1LtAD7frRMUTD3.json")
 suspend fun getRegistreredUsers():Response<List<RegisteredUser>>
 @GET("BonusUser.json")
 suspend fun getBounusUser():Response<BonusUser>
 @POST("PriceCountryInnoverit.json")
 suspend fun registerCostTotal(@Body costInnoverit:CostInnoverit):Response<CostInnoverit>

 @POST("product/send")
 @FormUrlEncoded
 fun sendProduct(
  @Field("apikey") apikey: String,
  @Field("id_product") idProduct: String,
  @Field("destination") destination: String,
  @Field("key") key: String,
  @Field("note") note: String
 ): Call<ResponseBody>

 @POST("product/get/balance")
  fun getBalance(@Query("apikey") apikey: String):Call<BalanceResponse>
}