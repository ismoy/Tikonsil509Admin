package com.tikonsil.tikonsil509admin.data.remote.api
import com.tikonsil.tikonsil509admin.domain.model.PushNotification
import com.tikonsil.tikonsil509admin.utils.Constant.Companion.CONTENT_TYPE
import com.tikonsil.tikonsil509admin.utils.Constant.Companion.SERVER_KEY
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/** * Created by ISMOY BELIZAIRE on 13/05/2022. */
interface NotificationAPI {
 @Headers(
  "Content-Type:$CONTENT_TYPE",
  "Authorization:key=$SERVER_KEY")
 @POST("fcm/send")
 suspend fun postNotification(
  @Body notification: PushNotification
 ):Response<ResponseBody>
}

