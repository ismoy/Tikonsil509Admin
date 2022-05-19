package com.tikonsil.tikonsil509admin.domain.model

import com.google.gson.annotations.SerializedName

/** * Created by ISMOY BELIZAIRE on 13/05/2022. */
data class RegisteredUser(
    @SerializedName("id") var id:String? = null,
    @SerializedName("firstname") var firstname:String? = null,
    @SerializedName("lastname") var lastname:String?= null,
    @SerializedName("email") var email:String?=null,
    @SerializedName("phone") var phone:String? = null,
    @SerializedName("role") var role:Int=3,
    @SerializedName("password") var password:String? = null,
    @SerializedName("status") var status:Int=1,
    @SerializedName("soldmoncash") var soldmoncash:Double,
    @SerializedName("soldtopup") var soldtopup:Double,
    @SerializedName("soldnatcash") var soldnatcash:Double,
    @SerializedName("soldlapoula") var soldlapoula:Double,
    @SerializedName("countrycode") var countrycode:String,
    @SerializedName("image") var image:String? = null
)
