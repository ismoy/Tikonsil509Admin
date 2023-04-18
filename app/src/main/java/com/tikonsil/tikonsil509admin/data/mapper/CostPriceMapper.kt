package com.tikonsil.tikonsil509admin.data.mapper

import com.tikonsil.tikonsil509admin.data.local.entity.CostInnoveritEntity
import com.tikonsil.tikonsil509admin.domain.model.CostInnoverit

fun CostInnoverit.toDomain(): CostInnoveritEntity{
    return CostInnoveritEntity(
        priceReceiver = this.priceReceiver,
        operatorName = this.operatorName,
        priceSales = this.priceSales,
        nameMoneyCountryReceiver = this.nameMoneyCountryReceiver,
        nameMoneyCountrySale = this.nameMoneyCountrySale,
        idProduct = this.idProduct,
        country = this.country,
        formatPrice = this.formatPrice,
        idKey = this.idKey
    )
}