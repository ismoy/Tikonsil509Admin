package com.tikonsil.tikonsil509admin.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.data.adapter.viewHolder.BaseListViewHolder
import com.tikonsil.tikonsil509admin.data.local.entity.CostInnoveritEntity
import com.tikonsil.tikonsil509admin.data.remote.provider.CostInnoveritProvider
import com.tikonsil.tikonsil509admin.databinding.BottomSheetUpdateListIdproductBinding
import com.tikonsil.tikonsil509admin.databinding.ItemListIdproductInnoverritBinding
import com.tikonsil.tikonsil509admin.domain.model.CostInnoverit

class CostInnoveritAdapter(private val context: Context) :
    androidx.recyclerview.widget.ListAdapter<CostInnoveritEntity , BaseListViewHolder<*>>(DiffUtilCallback) {
    private val bottomSheetDialog by lazy {
        BottomSheetDialog(
            context ,
            R.style.BottomSheetDialoTheme
        )
    }
    private val costInnoveritProvider by lazy { CostInnoveritProvider() }

    private object DiffUtilCallback : DiffUtil.ItemCallback<CostInnoveritEntity>() {
        override fun areItemsTheSame(oldItem: CostInnoveritEntity , newItem: CostInnoveritEntity): Boolean =
            oldItem.idProduct == newItem.idProduct

        override fun areContentsTheSame(oldItem: CostInnoveritEntity , newItem: CostInnoveritEntity): Boolean =
            oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): BaseListViewHolder<*> {
        val itemBinding = ItemListIdproductInnoverritBinding.inflate(
            LayoutInflater.from(parent.context) ,
            parent ,
            false
        )
        return BindViewHolderList(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseListViewHolder<*> , position: Int) {
        when (holder) {
            is CostInnoveritAdapter.BindViewHolderList -> holder.bind(getItem(position) , position)
        }
    }

    inner class BindViewHolderList(private val binding: ItemListIdproductInnoverritBinding) :
        BaseListViewHolder<CostInnoveritEntity>(binding.root) {
        override fun bind(item: CostInnoveritEntity , position: Int) = with(binding) {
            renderView(binding , item)

        }
    }

    private fun renderView(binding: ItemListIdproductInnoverritBinding , item: CostInnoveritEntity) {
        with(binding) {
            costInnoveritBinding = item
            containerList.setOnClickListener(onclickListener(item))
        }
    }

    private fun onclickListener(item: CostInnoveritEntity): View.OnClickListener {
        return View.OnClickListener {
            showBottomSheet(item)
        }

    }

    private fun showBottomSheet(item: CostInnoveritEntity) {
        val bottomViewBinding: BottomSheetUpdateListIdproductBinding =
            BottomSheetUpdateListIdproductBinding.inflate(LayoutInflater.from(context))
        bottomSheetDialog.setContentView(bottomViewBinding.root)
        bottomSheetDialog.setCancelable(false)
        bottomSheetDialog.show()
        with(bottomViewBinding) {
            dataBinding = item
            btnEditDialogCancel.setOnClickListener {
                bottomSheetDialog.dismiss()
            }
            btnEditDialog.setOnClickListener(updateData(item , bottomViewBinding))
        }
    }

    private fun updateData(
        item: CostInnoveritEntity ,
        bottomViewBinding: BottomSheetUpdateListIdproductBinding
    ): View.OnClickListener {
        return View.OnClickListener {
            costInnoveritProvider.updateStatus(
                item.idKey!! ,
                bottomViewBinding.priceReceiverDialog.text.toString() ,
                bottomViewBinding.productIdDialog.text.toString().toInt() ,
                bottomViewBinding.priceSaleDialog.text.toString(),
                "${bottomViewBinding.priceReceiverDialog.text.toString() } ${ item.nameMoneyCountryReceiver} ==> ${bottomViewBinding.priceSaleDialog.text.toString()} ${item.nameMoneyCountrySale} ==> ${item.operatorName}").addOnCompleteListener {
               if (it.isSuccessful){
                   Toast.makeText(context , "se ha actualizado los datos éxitosamente" , Toast.LENGTH_SHORT).show()
                   bottomSheetDialog.dismiss()
               }
            }
        }

    }
}