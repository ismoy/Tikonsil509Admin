package com.tikonsil.tikonsil509admin.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CostInnoveritEntity(
    val priceReceiver:String?=null,
    val operatorName:String?=null,
    val priceSales:String?=null,
    val nameMoneyCountryReceiver:String?=null,
    val nameMoneyCountrySale:String?=null,
    val idProduct:String?=null,
    val country:String?=null,
    val formatPrice:String?=null,
    var idKey:String?=null
){
    @PrimaryKey(autoGenerate = true)
    var id:Int =0
}
