package com.tikonsil.tikonsil509admin.data.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.data.adapter.viewHolder.BaseListViewHolder
import com.tikonsil.tikonsil509admin.data.local.entity.SalesErrorEntity
import com.tikonsil.tikonsil509admin.databinding.BottomSheetUpdateSalesErrorBinding
import com.tikonsil.tikonsil509admin.databinding.ItemFormHistorySalesErrorBinding
import com.tikonsil.tikonsil509admin.domain.model.SendRechargeProduct
import com.tikonsil.tikonsil509admin.domain.model.SendRechargeResponse
import com.tikonsil.tikonsil509admin.domain.repository.sendRecharge.SendRechargeRepository
import com.tikonsil.tikonsil509admin.utils.UtilsView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import javax.inject.Inject

/** * Created by ISMOY BELIZAIRE on 02/05/2022. */
class ErrorSalesAdapter(private val context: Activity) :
    ListAdapter<SalesErrorEntity , BaseListViewHolder<*>>(DiffUtilCallback) {

    val bottomSheetDialog by lazy { BottomSheetDialog(context , R.style.BottomSheetDialoTheme) }
    private val _responseInnoverit: MutableLiveData<Result<Call<SendRechargeResponse>>> by lazy { MutableLiveData() }
    val responseInnoverit: LiveData<Result<Call<SendRechargeResponse>>> = _responseInnoverit
    @Inject
    lateinit var  repository: SendRechargeRepository
    private object DiffUtilCallback : DiffUtil.ItemCallback<SalesErrorEntity>() {
        override fun areItemsTheSame(oldItem: SalesErrorEntity , newItem: SalesErrorEntity): Boolean =
            oldItem.email == newItem.email

        override fun areContentsTheSame(oldItem: SalesErrorEntity , newItem: SalesErrorEntity): Boolean =
            oldItem.email == newItem.email

    }


    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): BaseListViewHolder<*> {
        val itemBinding = ItemFormHistorySalesErrorBinding.inflate(
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

    inner class BindViewHolderList(private val binding: ItemFormHistorySalesErrorBinding) :
        BaseListViewHolder<SalesErrorEntity>(binding.root) {
        override fun bind(item: SalesErrorEntity , position: Int) {
            renderView(binding , item)
        }
    }

    private fun renderView(binding: ItemFormHistorySalesErrorBinding , item: SalesErrorEntity) {
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

    private fun onClickListener(item: SalesErrorEntity): View.OnClickListener {
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

    private fun showBottomSheet(item: SalesErrorEntity) {
        val bottomViewBinding: BottomSheetUpdateSalesErrorBinding =
            BottomSheetUpdateSalesErrorBinding.inflate(LayoutInflater.from(context))
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
        item: SalesErrorEntity,
        bottomViewBinding: BottomSheetUpdateSalesErrorBinding
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
