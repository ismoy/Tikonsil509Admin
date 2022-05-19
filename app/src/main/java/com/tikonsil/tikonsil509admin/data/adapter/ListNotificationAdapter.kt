package com.tikonsil.tikonsil509admin.data.adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.key
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.data.remote.provider.RemoveNotificationProvider
import com.tikonsil.tikonsil509admin.databinding.ItemListNotificationBinding
import com.tikonsil.tikonsil509admin.domain.model.NotificationList
import com.tikonsil.tikonsil509admin.ui.activity.detailsnotification.DetailsNotification
import com.tikonsil.tikonsil509admin.ui.activity.home.HomeActivity
import com.tikonsil.tikonsil509admin.ui.fragment.home.HomeFragment
import kotlinx.coroutines.flow.callbackFlow
import java.security.AccessController.getContext

/** * Created by ISMOY BELIZAIRE on 16/05/2022. */
class ListNotificationAdapter(private val context: Context) : RecyclerView.Adapter<ListNotificationAdapter.MyViewHolder>() {
 private var notificationList = mutableListOf<NotificationList>()

 fun setListDataNotification(data:MutableList<NotificationList>){
  notificationList = data
 }


 override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
 val view = ItemListNotificationBinding.inflate(LayoutInflater.from(parent.context),parent,false)
  return MyViewHolder(view)
 }

 override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
  val listdata =notificationList[position]
  holder.bidView(listdata,context)
 }

 override fun getItemCount(): Int {
  return notificationList.size
 }
 class MyViewHolder(private val binding:ItemListNotificationBinding) :  RecyclerView.ViewHolder(binding.root) {
  @SuppressLint("CheckResult", "SetTextI18n")
  fun bidView(datalist: NotificationList, context: Context) {
     val deleteuser by lazy { RemoveNotificationProvider() }
   val dialog by lazy { Dialog(context) }
      binding.apply {
       titlenotify.text =datalist.title
       messagenotification.text =datalist.message
       deletenotification.setOnClickListener {
        deleteuser.delete(datalist.key)
        context.startActivity(Intent(context,HomeActivity::class.java))
       }
       }
   itemView.setOnClickListener {
    dialog.setContentView(R.layout.custom_dialog_ldetails_notification)
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

    dialog.findViewById<TextView>(R.id.title).text= datalist.title
    dialog.findViewById<TextView>(R.id.message).text= datalist.message
    dialog.findViewById<TextView>(R.id.fechadesolicitud).text= datalist.date

    dialog.findViewById<Button>(R.id.leido).setOnClickListener {
     context.startActivity(Intent(context,HomeActivity::class.java))
     deleteuser.delete(datalist.key)
     dialog.dismiss()
    }
   }
  }
  }

 }
