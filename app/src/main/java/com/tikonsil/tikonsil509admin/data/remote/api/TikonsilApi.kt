package com.tikonsil.tikonsil509admin.data.remote.api

import com.tikonsil.tikonsil509.domain.model.*
import com.tikonsil.tikonsil509admin.domain.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

/** * Created by ISMOY BELIZAIRE on 23/04/2022. */
interface TikonsilApi {
 @PATCH("Admin/{uidUser}.json")
 suspend fun registerClient(
  @Path("uidUser") uidUser:String,@Body param: Users
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
 @POST
 suspend fun registerCostTotal(
  @Url url: String,
  @Body costInnoverit:CostInnoverit
 ):Response<CostInnoverit>

 @POST
 fun sendProduct(
  @HeaderMap authorization: Map<String , String>,
  @Url url: String ,
  @Body sendRechargeProduct: SendRechargeProduct,
 ): Call<SendRechargeResponse>

 @POST
 suspend fun postNotification(
  @Url url: String ,
  @Body notification: PushNotification,
  @HeaderMap authorization: Map<String, String>
 ): Response<PushNotification>
 @POST
  fun getBalance(
  @Url url: String,
  @HeaderMap authorization:Map<String,String>
 ):Call<BalanceResponse>

 @GET("secretKeyAuthorization.json")
 suspend fun getKeyAuthorization():Response<Authorization>
}

