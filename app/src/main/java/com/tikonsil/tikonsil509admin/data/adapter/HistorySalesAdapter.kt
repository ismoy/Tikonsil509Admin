package com.tikonsil.tikonsil509admin.data.adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.tikonsil.tikonsil509admin.domain.model.Sales
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.data.remote.api.RetrofitInstance
import com.tikonsil.tikonsil509admin.data.remote.api.RetrofitInstanceApiRechargeTikonsil509
import com.tikonsil.tikonsil509admin.data.remote.api.RetrofitInstanceFCM
import com.tikonsil.tikonsil509admin.data.remote.provider.UpdateStatusSalesProvider
import com.tikonsil.tikonsil509admin.databinding.ItemFormHistoryInvoicesBinding
import com.tikonsil.tikonsil509admin.domain.model.NotificationData
import com.tikonsil.tikonsil509admin.domain.model.PushNotification
import com.tikonsil.tikonsil509admin.domain.model.SendRecharge
import com.tikonsil.tikonsil509admin.ui.activity.home.HomeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

/** * Created by ISMOY BELIZAIRE on 02/05/2022. */
class HistorySalesAdapter(val context: Context): RecyclerView.Adapter<HistorySalesAdapter.MyViewHolder>() {
 private var saleList = mutableListOf<Sales>()
 lateinit var dialogPopUp:Dialog
 fun setsaleListDataHistory(data:MutableList<Sales>) {
  saleList = data
 }
 override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
 val view = ItemFormHistoryInvoicesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
  return MyViewHolder(view)
 }

 override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
  val saledata =saleList[position]
  holder.bidView(saledata,context)

  setOnClickItemListener(holder.itemView,saledata)
 }

 private fun setOnClickItemListener(itemView: View , saledata: Sales) {
  val dialog by lazy { Dialog(context) }
  itemView.setOnClickListener {
   dialog.setContentView(R.layout.custom_update_sales_dialog)
   val window: Window? = dialog.window
   window?.setLayout(
    ViewGroup.LayoutParams.MATCH_PARENT,
    ViewGroup.LayoutParams.WRAP_CONTENT
   )
   dialog.setCancelable(false)
   if (dialog.window != null) {
    dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
   }
   dialog.show()

   dialog.apply {
    val button_cancel = findViewById<Button>(R.id.btn_edit_dialog_cancel)
    val btn_edit_dialog = findViewById<Button>(R.id.btn_edit_dialog)
    val firstnamedialog = findViewById<TextInputEditText>(R.id.firstnamedialog)
    val lastnamedialog = findViewById<TextInputEditText>(R.id.lastnamedialog)
    val passworddialog = findViewById<TextInputEditText>(R.id.passworddialog)
    val layoutpasswordname = findViewById<TextInputLayout>(R.id.layoutpasswordname)
    val cardContainer = findViewById<CardView>(R.id.cardContainer)
    val cardContainerDialog = findViewById<CardView>(R.id.cardViewDialog)
    //set data in edit text
     firstnamedialog.setText(saledata.salesPrice)
     lastnamedialog.setText(saledata.idProduct.toString())
     passworddialog.setText(saledata.status.toString())
    button_cancel.setOnClickListener {
     dialog.dismiss()
    }
    if (saledata.idProduct==0){
     btn_edit_dialog.visibility=View.GONE
    }
    btn_edit_dialog.setOnClickListener {
     if (passworddialog.text.toString().isNotEmpty()){
      sendRecharge(saledata,passworddialog,cardContainer,cardContainerDialog)
     }else{
      layoutpasswordname.helperText ="Ingrese un numero 0 o 1"
     }
    }
   }

  }
 }


 private fun sendRecharge(
  saledata: Sales ,
  passworddialog: TextInputEditText ,
  cardContainer: CardView ,
  cardContainerDialog: CardView
 ) {
  cardContainerDialog.visibility =View.VISIBLE
  cardContainer.visibility = View.GONE
  GlobalScope.launch {
   val getAuthorizationKey = RetrofitInstance.tikonsilApi.getKeyAuthorization()
   if (getAuthorizationKey.isSuccessful){
    val headers = HashMap<String,String>()
    headers["Authorization"] = getAuthorizationKey.body()!!.key
    val call = RetrofitInstanceApiRechargeTikonsil509.tikonsilApi.sendProduct(saledata.idProduct.toString(),saledata.phone.toString(),headers)
    val updateStatus = UpdateStatusSalesProvider()
    call.enqueue(object: Callback<SendRecharge> {
     override fun onResponse(call: Call<SendRecharge> , response: Response<SendRecharge>) {
      if (response.isSuccessful){
       try {
        val responseString =response.body().toString()
        Log.d("responseApi",responseString)
        if (response.body()?.status =="success"){
         updateStatus.updateStatus(saledata.idKey,passworddialog.text.toString())
         context.startActivity(Intent(context.applicationContext,HomeActivity::class.java))
         Toast.makeText(context , "Has recargado con éxito el numero telefono" , Toast.LENGTH_LONG).show()
         sendNotificationToOtherDevice(saledata.token)
         cardContainerDialog.visibility =View.GONE
         cardContainer.visibility = View.VISIBLE
        }else{
         Toast.makeText(context.applicationContext , "No fue posible realizar la recarga pon en contacto con su proveedor" , Toast.LENGTH_SHORT).show()
         Log.d("responseApi",response.body().toString())
        }
       }catch (e: IOException){
        Toast.makeText(context.applicationContext , "No fue posible realizar la recarga" , Toast.LENGTH_SHORT).show()
        Log.d("responseApi",e.message.toString())
        cardContainerDialog.visibility =View.GONE
        cardContainer.visibility = View.VISIBLE
       }
      }else{
       Log.d("ErrorResponseApi",response.errorBody().toString())
       Log.d("ErrorResponseApi",response.code().toString())
       cardContainerDialog.visibility =View.GONE
       cardContainer.visibility = View.VISIBLE
       Toast.makeText(context.applicationContext , "No fue posible realizar la recarga" , Toast.LENGTH_SHORT).show()
      }
     }

     override fun onFailure(call: Call<SendRecharge> , t: Throwable) {
      Log.d("ErrorResponseApi",t.toString())
      cardContainerDialog.visibility =View.GONE
      cardContainer.visibility = View.VISIBLE
      Toast.makeText(context.applicationContext , "No fue posible realizar la recarga" , Toast.LENGTH_SHORT).show()
     }

    })
   }
  }


 }

 override fun getItemCount(): Int {
  return saleList.size
 }
 class MyViewHolder(private val binding: ItemFormHistoryInvoicesBinding) :  RecyclerView.ViewHolder(binding.root) {
  fun bidView(saledata: Sales , context: Context) {
   binding.apply {
    firstnameinvoicesagente.text =saledata.firstname
    lastnameinvoicesagente.text =saledata.lastname
    emailinvoicesagente.text =saledata.email
    saledata.role.let {
     if (it==1){
      roleinvoicesagente.text =context.getString(R.string.agent)
     }else{
      roleinvoicesagente.text =context.getString(R.string.master)
     }
    }
    telefonoinvoiceagente.text =saledata.phone
    fechainvoiceagente.text =saledata.date
    tiporecargainvoiceagente.text =saledata.typerecharge
    paisinvoiceagente.text = saledata.country
    subtotalinvoiceagente.text =saledata.subtotal
    descriptioninvoiceagente.text =saledata.description

    if (saledata.salesPrice == "" && saledata.idProduct==0){
     changeStatus.text =context.getString(R.string.Finalized)
     changeStatus.background =context.resources.getDrawable(R.drawable.background_confirmed)
    }
    if (saledata.status==0 && saledata.salesPrice!=""){
     changeStatus.text =context.getString(R.string.pending)
     changeStatus.background =context.resources.getDrawable(R.drawable.background_pending)
    }
    else{
     changeStatus.text =context.getString(R.string.Finalized)
     changeStatus.background =context.resources.getDrawable(R.drawable.background_confirmed)
    }
    Glide.with(context).load(saledata.image).into(pictureregisteruseragente)
   }
  }

 }

 private fun sendNotificationToOtherDevice(token: String?) {
  PushNotification(
   NotificationData(
    context.getString(R.string.recharge_tikonsil) ,
    context.getString(R.string.aprobado)) ,
   token!!
  ).also { push ->
   sendNotification(push)

  }
 }

 private fun sendNotification(notification: PushNotification) =
  CoroutineScope(Dispatchers.IO).launch {
   try {
    val response = RetrofitInstanceFCM.notificationAPI.postNotification(notification)
    if (response.isSuccessful) {
    } else {
     Toast.makeText(
      context,
      "Error: ${response.errorBody().toString()}",
      Toast.LENGTH_SHORT
     ).show()
    }
   } catch (e: Exception) {
   }
  }
}