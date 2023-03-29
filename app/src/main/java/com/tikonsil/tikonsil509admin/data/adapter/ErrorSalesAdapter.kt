package com.tikonsil.tikonsil509admin.data.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.data.adapter.viewHolder.BaseListViewHolder
import com.tikonsil.tikonsil509admin.databinding.BottomSheetUpdateBinding
import com.tikonsil.tikonsil509admin.databinding.ItemFormHistoryInvoicesBinding
import com.tikonsil.tikonsil509admin.domain.model.Sales
import com.tikonsil.tikonsil509admin.domain.model.SendRechargeProduct
import com.tikonsil.tikonsil509admin.domain.model.SendRechargeResponse
import com.tikonsil.tikonsil509admin.domain.repository.sendRecharge.SendRechargeRepository
import com.tikonsil.tikonsil509admin.presentation.historysales.HistorySalesViewModel
import com.tikonsil.tikonsil509admin.utils.UtilsView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call

/** * Created by ISMOY BELIZAIRE on 02/05/2022. */
class ErrorSalesAdapter(private val context: Activity) :
    ListAdapter<Sales , BaseListViewHolder<*>>(DiffUtilCallback) {

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
            is ErrorSalesAdapter.BindViewHolderList -> holder.bind(getItem(position) , position)
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
                "keyIdUpdateStatusErrorSales" ,
                item.idKey
            )
            UtilsView.setValueSharedPreferences(context , "TokenUser" , item.token!!)
            UtilsView.setValueSharedPreferences(
                context ,
                "IdProductPending" ,
                item.idProduct.toString()
            )
            UtilsView.setValueSharedPreferences(context , "IdUserPending" , item.idUser.toString())
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
