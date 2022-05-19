package com.tikonsil.tikonsil509.domain.model

import com.google.gson.annotations.SerializedName

/** * Created by ISMOY BELIZAIRE on 23/04/2022. */
data class Users(
 @SerializedName("id") var id:String? = null,
 @SerializedName("countryselected") var countryselected:String? = null,
 @SerializedName("countrycode") val countrycode:String?=null,
 @SerializedName("firstname") var firstname:String? = null,
 @SerializedName("lastname") var lastname:String?= null,
 @SerializedName("email") var email:String?=null,
 @SerializedName("phone") var phone:String? = null,
 @SerializedName("role") var role:Int=3,
 @SerializedName("password") var password:String? = null,
 @SerializedName("confirmpassword") var confirmpassword:String? = null,
 @SerializedName("status") var status:Int=1,
 @SerializedName("image") var image:String? = null
)
