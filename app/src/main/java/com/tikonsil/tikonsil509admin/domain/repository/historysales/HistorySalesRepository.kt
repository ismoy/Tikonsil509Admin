package com.tikonsil.tikonsil509admin.domain.repository.historysales

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.tikonsil.tikonsil509admin.data.remote.api.RetrofitInstanceApiRechargeInnoverit
import com.tikonsil.tikonsil509admin.domain.model.Sales
import com.tikonsil.tikonsil509admin.data.remote.provider.HistorySalesProvider
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

/** * Created by ISMOY BELIZAIRE on 14/05/2022. */
class HistorySalesRepository {
    private val historysalesprovider by lazy { HistorySalesProvider() }

    var isExistSnapshot = MutableLiveData<Boolean>()
     fun getHistorySales(): LiveData<MutableList<Sales>> {
        val mutableLiveDat = MutableLiveData<MutableList<Sales>>()
        historysalesprovider.getHistorySales()?.addValueEventListener(object : ValueEventListener {
            val listlastdata = mutableListOf<Sales>()
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (ds in snapshot.children){
                        val firstname = ds.child("firstname").value.toString()
                        val lastname =ds.child("lastname").value.toString()
                        val phone = ds.child("phone").value.toString()
                        val role =ds.child("role").value.toString()
                        val email =ds.child("email").value.toString()
                        val description = ds.child("description").value.toString()
                        val subtotal = ds.child("subtotal").value.toString()
                        val date = ds.child("date").value.toString()
                        val country = ds.child("country").value.toString()
                        val codecountry =ds.child("codecountry").value.toString()
                        val typerecharge = ds.child("typerecharge").value.toString()
                        val token = ds.child("token").value.toString()
                        val status = ds.child("status").value.toString()
                        val idProduct = ds.child("id_product").value.toString()
                        val salePrice = ds.child("salesPrice").value.toString()
                        val imageView = ds.child("image").value.toString()
                        val listsales = Sales("",firstname,lastname, email,role.toInt(),typerecharge, phone, date,country,codecountry, subtotal, description,token,status.toInt(),idProduct.toInt(),salePrice,ds.key.toString(),imageView)
                        listlastdata.add(listsales)
                    }
                    mutableLiveDat.value =listlastdata
                }else{
                    isExistSnapshot.value =true
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
        return mutableLiveDat
    }

    fun sendProduct(apikey:String,id_product:String,destination:String,key:String,note:String){
        val call = RetrofitInstanceApiRechargeInnoverit.tikonsilApi.sendProduct(apikey,id_product,destination,key,note)
        call.enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody> , response: Response<ResponseBody>) {
                if (response.isSuccessful){
                    try {
                        val responseString =response.body()!!.string()
                        Log.d("responseApi",responseString)
                    }catch (e: IOException){
                        Log.d("responseApi",e.message.toString())
                    }
                }else{
                    Log.d("ErrorResponseApi",response.errorBody().toString())
                    Log.d("ErrorResponseApi",response.code().toString())
                }
            }

            override fun onFailure(call: Call<ResponseBody> , t: Throwable) {
                Log.d("ErrorResponseApi",t.toString())
            }

        })
    }
}