package com.tikonsil.tikonsil509admin.data.remote.provider

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/** * Created by ISMOY BELIZAIRE on 01/05/2022. */
class PriceCountryProvider {
 var mDatabase:DatabaseReference?= FirebaseDatabase.getInstance().reference.child("CountryPrice")

 fun getCountryPrice(): DatabaseReference? {
  return mDatabase
 }

 fun updatePrice(idKey:String,priceMonCash:String,priceLaPouLa:String,priceNatCash:String):Task<Void>{
  val map:MutableMap<String?,Any> = HashMap()
  map["priceMonCash"] = priceMonCash
  map["priceLaPouLa"] = priceLaPouLa
  map["priceNatCash"] = priceNatCash
  return idKey.let { mDatabase!!.child(it).updateChildren(map) }
 }

}