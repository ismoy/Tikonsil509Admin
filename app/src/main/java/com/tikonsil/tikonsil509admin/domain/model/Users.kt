package com.tikonsil.tikonsil509admin.domain.model

import com.google.gson.annotations.SerializedName

/** * Created by ISMOY BELIZAIRE on 13/05/2022. */
data class Users(
    @SerializedName("id") var id:String? = null,
    @SerializedName("countryselected") var countryselected:String? =null,
    @SerializedName("firstname") var firstname:String? = null,
    @SerializedName("lastname") var lastname:String?= null,
    @SerializedName("email") var email:String?=null,
    @SerializedName("phone") var phone:String? = null,
    @SerializedName("role") var role:Int?=null,
    @SerializedName("password") var password:String? = null,
    @SerializedName("status") var status:Int?=null,
    @SerializedName("soldmoncash") var soldmoncash:Double?=null,
    @SerializedName("soldtopup") var soldtopup:Double?=null,
    @SerializedName("soldnatcash") var soldnatcash:Double?=null,
    @SerializedName("soldlapoula") var soldlapoula:Double?=null,
    @SerializedName("countrycode") var countrycode:String?=null,
    @SerializedName("image") var image:String? = null){
    constructor(): this(null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null)
}
