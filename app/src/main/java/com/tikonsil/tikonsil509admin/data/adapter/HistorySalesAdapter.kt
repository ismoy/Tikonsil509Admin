package com.tikonsil.tikonsil509admin.data.adapter

import android.annotation.SuppressLint
import android.app.Activity
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
import android.widget.ListAdapter
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.data.adapter.viewHolder.BaseListViewHolder
import com.tikonsil.tikonsil509admin.data.remote.provider.UpdateStatusSalesProvider
import com.tikonsil.tikonsil509admin.data.remote.provider.firebaseApi.FirebaseApi
import com.tikonsil.tikonsil509admin.data.remote.retrofitInstance.Headers
import com.tikonsil.tikonsil509admin.data.remote.retrofitInstance.RetrofitInstance
import com.tikonsil.tikonsil509admin.databinding.BottomSheetUpdateBinding
import com.tikonsil.tikonsil509admin.databinding.ItemFormHistoryInvoicesBinding
import com.tikonsil.tikonsil509admin.domain.model.*
import com.tikonsil.tikonsil509admin.domain.repository.sendRecharge.SendRechargeRepository
import com.tikonsil.tikonsil509admin.ui.activity.home.HomeActivity
import com.tikonsil.tikonsil509admin.utils.UtilsView
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

/** * Created by ISMOY BELIZAIRE on 02/05/2022. */
class HistorySalesAdapter(private val context: Activity):
 androidx.recyclerview.widget.ListAdapter<Sales , BaseListViewHolder<*>>(DiffUtilCallback) {

 val bottomSheetDialog by lazy { BottomSheetDialog(context , R.style.BottomSheetDialoTheme) }
 private val repository: SendRechargeRepository = SendRechargeRepository()
 private val _responseInnoverit: MutableLiveData<Result<Call<SendRechargeResponse>>> by lazy { MutableLiveData() }
 val responseInnoverit: LiveData<Result<Call<SendRechargeResponse>>> = _responseInnoverit
 private object DiffUtilCallback : DiffUtil.ItemCallback<Sales>() {
  override fun areItemsTheSame(oldItem: Sales , newItem: Sales): Boolean =
   oldItem.email == newItem.email

  override fun areContentsTheSame(oldItem: Sales , newItem: Sales): Boolean =
   oldItem == newItem

 }
 override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): BaseListViewHolder<*> {
  val itemBinding = ItemFormHistoryInvoicesBinding.inflate(
   LayoutInflater.from(parent.context) ,
   parent ,
   false
  )
  return BindViewHolderList(itemBinding)
 }

 override fun onBindViewHolder(holder: BaseListViewHolder<*> , position: Int) {
  when (holder) {
   is HistorySalesAdapter.BindViewHolderList -> holder.bind(getItem(position) , position)
  }
 }

 inner class BindViewHolderList(private val binding: ItemFormHistoryInvoicesBinding) :
  BaseListViewHolder<Sales>(binding.root) {
  override fun bind(item: Sales , position: Int) = with(binding) {
   renderView(binding , item)

  }
 }


 private fun renderView(binding: ItemFormHistoryInvoicesBinding , item: Sales) {
  with(binding) {
   salesBinding = item
   if (item.status == 0) {
    changeStatus.text = status.context.getString(R.string.pending)
    changeStatus.background = ResourcesCompat.getDrawable(
     status.context.resources ,
     R.drawable.background_pending ,
     null
    )
   } else {
    changeStatus.text = status.context.getString(R.string.Finalized)
    changeStatus.background = ResourcesCompat.getDrawable(
     status.context.resources ,
     R.drawable.background_confirmed ,
     null
    )
   }
   Glide.with(status.context).load(item.image).into(pictureregisteruseragente)
   if (item.role == 1) {
    roleinvoicesagente.text = "Agente"
   } else {
    roleinvoicesagente.text = "Master"
   }
   if (item.status!=1){
    container.setOnClickListener(onClickListener(item))
   }
  }
 }


 private fun onClickListener(item: Sales): View.OnClickListener {
  return View.OnClickListener {
   showBottomSheet(item)
   UtilsView.setValueSharedPreferences(
    context ,
    "keyIdUpdateStatusHistorySales" ,
    item.idKey
   )
   UtilsView.setValueSharedPreferences(context , "TokenUserHistory" , item.token!!)
   UtilsView.setValueSharedPreferences(
    context ,
    "IdProductPendingHistory" ,
    item.idProduct.toString()
   )
   UtilsView.setValueSharedPreferences(context , "IdUserHistoryPending" , item.idUser.toString())
  }
 }

 private fun showBottomSheet(item: Sales) {
  val bottomViewBinding: BottomSheetUpdateBinding =
   BottomSheetUpdateBinding.inflate(LayoutInflater.from(context))
  bottomSheetDialog.setContentView(bottomViewBinding.root)
  bottomSheetDialog.setCancelable(false)
  bottomSheetDialog.show()
  with(bottomViewBinding) {
   dataBinding = item
   productIdDialog.setText(item.idProduct.toString())
   statusDialog.setText(item.status.toString())
   btnEditDialogCancel.setOnClickListener {
    bottomSheetDialog.dismiss()
   }
   btnEditDialog.setOnClickListener(sendRecharge(item , bottomViewBinding))
  }
 }

 @OptIn(DelicateCoroutinesApi::class)
 @SuppressLint("SuspiciousIndentation")
 private fun sendRecharge(
  item: Sales ,
  bottomViewBinding: BottomSheetUpdateBinding
 ): View.OnClickListener {
  return View.OnClickListener {
   val sendProduct = SendRechargeProduct(
    bottomViewBinding.productIdDialog.text.toString().toInt() ,
    item.phone!! ,
    item.email!!
   )
   GlobalScope.launch {
    val response = repository.sendRechargeViaInnoverit(sendProduct)
    context.runOnUiThread {
     _responseInnoverit.value = response
    }
   }
  }
 }

}