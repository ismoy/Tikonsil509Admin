package com.tikonsil.tikonsil509admin.utils

import android.util.Patterns
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.math.floor

/** * Created by ISMOY BELIZAIRE on 23/04/2022. */
class Constant {
 companion object{
  const val BASE_URL = "https://tikonsil509-ea2cc-default-rtdb.firebaseio.com/"
  const val VALIDATERUT ="^[0-9]+-[0-9kK]{1}$"
  const val MATCHES ="^[ a-zA-Z\\u00F1\\u00D1]+\$"
 }
 //validate email
 fun validateEmail(email:String?):Boolean{
  val pattern =Patterns.EMAIL_ADDRESS;
  return pattern.matcher(email).matches()
 }
 //validate rut
 fun validaterut(rut:String?):Boolean{
  val pattern = Pattern.compile(VALIDATERUT)
  val matcher:Matcher = pattern.matcher(rut)
  if (!matcher.matches()) return false
  val stringRut = rut?.split("-")?.toTypedArray()
  return stringRut?.get(1)?.lowercase(Locale.getDefault())== this.dv(stringRut?.get(0))
 }
 //validate digit code
 fun dv(rut:String?):String?{
  var M:Int = 0
  var S = 1
  var T = rut?.toInt()
  while (T != 0){
   if (T != null) {
    S = (S + T % 10 *(9 -M++ % 6)) %11
   }
   T = 10.let{ T = T?.div(it); T}?.let { floor(it.toDouble()).toInt() }
  }
  return if (S>0)(S -1).toString() else "k"
 }

 //validate only letter
 fun validateonlyleter(datos:String):Boolean{
  return datos.matches(MATCHES.toRegex())
 }
 //validate number phone
 fun validatelenghnumberphone(numbers:String):Boolean{
  return numbers.length == 9
 }
 fun validateonlynumberone(numbers: Int):Boolean{
  return numbers == 1
 }
 fun validateonlynumbertwo(numbers:Int):Boolean{
  return numbers == 2
 }
 //validate longitude password
 fun validatelongitudepassword(numbers: String):Boolean{
  return numbers.length>=8
 }
}