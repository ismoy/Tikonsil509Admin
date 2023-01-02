package com.tikonsil.tikonsil509admin.data.remote.provider

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/** * Created by ISMOY BELIZAIRE on 01/05/2022. */
class PriceCountryProvider {
 var mDatabase:DatabaseReference?= FirebaseDatabase.getInstance().reference.child("CountryPrice")
 suspend fun getCountryPrice(): DatabaseReference? {
  return mDatabase
 }

}