package com.tikonsil.tikonsil509admin.domain.model

import java.io.Serializable

/** * Created by ISMOY BELIZAIRE on 16/05/2022. */
data class NotificationList(
 val title:String,
 val message:String,
 val idUser:String,
 val key:String,
 val date:String
):Serializable
