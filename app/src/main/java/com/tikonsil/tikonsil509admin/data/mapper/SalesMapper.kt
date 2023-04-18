package com.tikonsil.tikonsil509admin.data.mapper

import com.tikonsil.tikonsil509admin.data.local.entity.SalesEntity
import com.tikonsil.tikonsil509admin.data.local.entity.SalesErrorEntity
import com.tikonsil.tikonsil509admin.domain.model.Sales


fun Sales.toDomain(): SalesEntity {
    return SalesEntity(
        role = this.role,
        idUser= this.idUser,
        firstname = this.firstname,
        lastname = this.lastname,
        email = this.email,
        typerecharge = this.typerecharge,
        phone =this.phone,
        date = this.date,
        country = this.country,
        codecountry = this.codecountry,
        subtotal = this.subtotal,
        description = this.description,
        token = this.token,
        status = this.status,
        idProduct = this.idProduct?:"",
        salesPrice = this.salesPrice?:"",
        idKey = this.idKey?:"",
        image = this.image
    )
}

fun Sales.toSalesErrorDomain(): SalesErrorEntity {
    return SalesErrorEntity(
        role = this.role,
        idUser= this.idUser,
        firstname = this.firstname,
        lastname = this.lastname,
        email = this.email,
        typerecharge = this.typerecharge,
        phone =this.phone,
        date = this.date,
        country = this.country,
        codecountry = this.codecountry,
        subtotal = this.subtotal,
        description = this.description,
        token = this.token,
        status = this.status,
        idProduct = this.idProduct?:"",
        salesPrice = this.salesPrice?:"",
        idKey = this.idKey?:"",
        image = this.image
    )
}
