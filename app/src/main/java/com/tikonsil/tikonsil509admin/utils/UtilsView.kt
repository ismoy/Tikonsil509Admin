package com.tikonsil.tikonsil509admin.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.widget.ProgressBar
import androidx.core.view.isGone
import com.google.android.material.button.MaterialButton

object UtilsView {

    fun getValueSharedPreferences(activity: Activity , value: String): String {
        val sharedPreferences: SharedPreferences =
            activity.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString(value, "").toString()
    }

    fun setValueSharedPreferences(activity: Activity , key: String , value: String) {
        val sharedPreferences: SharedPreferences =
            activity.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putString(key, value)
            .apply()
    }

    fun clearSharedPreferences(context: Context, sharedPreferencesName: String) {
        val sharedPreferences = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }


    fun showProgress(materialButton: MaterialButton,progressBar: ProgressBar){
        materialButton.isEnabled = false
        materialButton.text = ""
        progressBar.isGone = false
    }

    fun hideProgress(materialButton: MaterialButton,progressBar: ProgressBar,text:String){
        materialButton.isEnabled = true
        materialButton.text = text
        progressBar.isGone = true
    }
}