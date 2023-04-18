package com.tikonsil.tikonsil509admin.domain.model


data class CostInnoverit(
    val priceReceiver:String?=null,
    val operatorName:String?=null,
    val priceSales:String?=null,
    val nameMoneyCountryReceiver:String?=null,
    val nameMoneyCountrySale:String?=null,
    val idProduct:String?=null,
    val country:String?=null,
    val formatPrice:String?=null,
    var idKey:String?=null
) {
    constructor():this(null,null,null,null,
    null,null,null,null,"")
}
