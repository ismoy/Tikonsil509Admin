package com.tikonsil.tikonsil509admin.domain.model

/** * Created by ISMOY BELIZAIRE on 13/05/2022. */
data class PushNotification(
    val data:NotificationData,
    val to: String
)

data class NotificationData(
    val title:String,
    val message:String
)
