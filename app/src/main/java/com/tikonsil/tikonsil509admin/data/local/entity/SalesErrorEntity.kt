package com.tikonsil.tikonsil509admin.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class SalesErrorEntity (
    val role: Int = 0,
    val idUser: String? = null,
    val firstname: String? = null,
    val lastname: String? = null,
    val email: String? = null,
    val typerecharge: String? = null,
    val phone: String? = null,
    val date: String? = null,
    val country: String? = null,
    val codecountry: String? = null,
    val subtotal: String? = null,
    val description: String? = null,
    val token: String? = null,
    val status: Int = 0,
    val idProduct: String? =null,
    val salesPrice: String,
    val idKey: String,
    val image: String
){
    @PrimaryKey(autoGenerate = true)
    var id:Int =0
}