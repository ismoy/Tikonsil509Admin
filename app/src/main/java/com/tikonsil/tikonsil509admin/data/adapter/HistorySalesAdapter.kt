package com.tikonsil.tikonsil509admin.data.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.data.adapter.viewHolder.BaseListViewHolder
import com.tikonsil.tikonsil509admin.data.local.entity.SalesEntity
import com.tikonsil.tikonsil509admin.databinding.BottomSheetUpdateBinding
import com.tikonsil.tikonsil509admin.databinding.ItemFormHistoryInvoicesBinding
import com.tikonsil.tikonsil509admin.domain.model.*
import com.tikonsil.tikonsil509admin.domain.repository.sendRecharge.SendRechargeRepository
import com.tikonsil.tikonsil509admin.utils.UtilsView
import kotlinx.coroutines.*
import retrofit2.Call
import javax.inject.Inject

/** * Created by ISMOY BELIZAIRE on 02/05/2022. */
class HistorySalesAdapter @Inject constructor(private val repository: SendRechargeRepository,private val context: Activity):
 androidx.recyclerview.widget.ListAdapter<SalesEntity , BaseListViewHolder<*>>(DiffUtilCallback) {

 val bottomSheetDialog by lazy { BottomSheetDialog(context , R.style.BottomSheetDialoTheme) }
 private val _responseInnoverit: MutableLiveData<Result<Call<SendRechargeResponse>>> by lazy { MutableLiveData() }
 val responseInnoverit: LiveData<Result<Call<SendRechargeResponse>>> = _responseInnoverit

 private object DiffUtilCallback : DiffUtil.ItemCallback<SalesEntity>() {
  override fun areItemsTheSame(oldItem: SalesEntity , newItem: SalesEntity): Boolean =
   oldItem.email == newItem.email

  override fun areContentsTheSame(oldItem: SalesEntity , newItem: SalesEntity): Boolean =
   oldItem.email == newItem.email

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
  BaseListViewHolder<SalesEntity>(binding.root) {
  override fun bind(item: SalesEntity , position: Int) = with(binding) {
   renderView(binding , item)

  }
 }


 private fun renderView(binding: ItemFormHistoryInvoicesBinding , item: SalesEntity) {
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


 private fun onClickListener(item: SalesEntity): View.OnClickListener {
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

 private fun showBottomSheet(item: SalesEntity) {
  val bottomViewBinding: BottomSheetUpdateBinding =
   BottomSheetUpdateBinding.inflate(LayoutInflater.from(context))
  bottomSheetDialog.setContentView(bottomViewBinding.root)
  bottomSheetDialog.setCancelable(false)
  bottomSheetDialog.show()
  with(bottomViewBinding) {
   dataBinding = item
   Log.e("idProduct",item.toString())
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
  item: SalesEntity ,
  bottomViewBinding: BottomSheetUpdateBinding
 ): View.OnClickListener {
  return View.OnClickListener {
   val sendProduct = SendRechargeProduct(
    bottomViewBinding.productIdDialog.text.toString() ,
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