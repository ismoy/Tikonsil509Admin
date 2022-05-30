package com.tikonsil.tikonsil509admin.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport.Session.User
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.domain.model.TypeBonusItem


/** * Created by ISMOY BELIZAIRE on 02/05/2022. */
class TypeBonusPriceAdapter(mContext: Context, userList: ArrayList<TypeBonusItem>) :
 BaseAdapter() {
 var mContext: Context
 var userList: ArrayList<TypeBonusItem>
 override fun getCount(): Int {
  return userList.size
 }

 override fun getItem(position: Int): Any {
  return userList[position]
 }

 override fun getItemId(position: Int): Long {
  return userList[position].id.toLong()
 }


 override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
  var convertView = convertView
  if (convertView == null) {
   convertView = LayoutInflater.from(mContext).inflate(R.layout.item_bonus_price, null, false)
   val textView = convertView!!.findViewById<TextView>(R.id.bonus_price)
   textView.text = userList[position].name
  }
  return convertView
 }

 init {
  this.mContext = mContext
  this.userList = userList
 }
}
