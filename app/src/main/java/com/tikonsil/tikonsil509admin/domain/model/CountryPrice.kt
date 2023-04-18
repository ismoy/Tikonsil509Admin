package com.tikonsil.tikonsil509admin.domain.model

/** * Created by ISMOY BELIZAIRE on 29/04/2022. */
data class CountryPrice(
 val priceLaPouLa:String,
 val priceMonCash:String,
 val priceNatCash:String,
 var idKey:String?=null
 ){
 constructor():this("0","0","0",null)
}
