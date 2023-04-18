package com.tikonsil.tikonsil509admin.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.core.view.isGone
import androidx.core.widget.doOnTextChanged
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.domain.model.Users
import java.util.*
import kotlin.collections.ArrayList

object UtilsView {

    fun getValueSharedPreferences(activity: Activity, value: String): String {
        val sharedPreferences: SharedPreferences =
            activity.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString(value, "").toString()
    }

    fun setValueSharedPreferences(activity: Activity, key: String, value: String) {
        val sharedPreferences: SharedPreferences =
            activity.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putString(key, value)
            .apply()
    }

    fun clearSharedPreferences(context: Context, sharedPreferencesName: String) {
        val sharedPreferences =
            context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }


    fun showProgress(materialButton: MaterialButton, progressBar: ProgressBar) {
        materialButton.isEnabled = false
        materialButton.text = ""
        progressBar.isGone = false
    }

    fun hideProgress(materialButton: MaterialButton, progressBar: ProgressBar, text: String) {
        materialButton.isEnabled = true
        materialButton.text = text
        progressBar.isGone = true
    }

    fun countryPriceValidator(
        textInputLayoutMon_cash: TextInputLayout, priceMon_cash: TextInputEditText,
        textInputLayoutLa_pou_la: TextInputLayout, priceLa_pou_la: TextInputEditText,
        textInputLayoutNat_cash: TextInputLayout, priceNat_cash: TextInputEditText,
        btnUpdate:MaterialButton,context: Context) {

        priceMon_cash.doOnTextChanged { txt_Price_mon_cash, _, _, _ ->
            when{
                txt_Price_mon_cash.toString().isEmpty() ->{
                    textInputLayoutMon_cash.helperText = context.getText(R.string.erroremptyfield)
                    btnUpdate.isEnabled = false
                    return@doOnTextChanged
                }else ->{
                textInputLayoutMon_cash.helperText = ""
                btnUpdate.isEnabled = true
                }
            }
        }

        priceLa_pou_la.doOnTextChanged { txt_Price_la_pou_la, _, _, _ ->
            when{
                txt_Price_la_pou_la.toString().isEmpty() ->{
                    textInputLayoutLa_pou_la.helperText = context.getText(R.string.erroremptyfield)
                    btnUpdate.isEnabled = false
                    return@doOnTextChanged
                }else ->{
                textInputLayoutLa_pou_la.helperText = ""
                btnUpdate.isEnabled = true
            }
            }
        }

        priceNat_cash.doOnTextChanged { txt_Price_nat_cash, _, _, _ ->
            when{
                txt_Price_nat_cash.toString().isEmpty() ->{
                    textInputLayoutNat_cash.helperText = context.getText(R.string.erroremptyfield)
                    btnUpdate.isEnabled = false
                    return@doOnTextChanged
                }else ->{
                textInputLayoutNat_cash.helperText = ""
                btnUpdate.isEnabled = true
            }
            }
        }
    }

    fun loginValidator(
        textInputLayoutEmail: TextInputLayout,
        email: TextInputEditText,
        textInputLayoutPassword: TextInputLayout,
        password: TextInputEditText,
        context: Context,
        mConstant: Constant
    ) {
        email.doOnTextChanged { txtEmail, _, _, _ ->
            when {
                txtEmail!!.toString().isEmpty() -> {
                    textInputLayoutEmail.helperText = context.getText(R.string.erroremptyfield)
                    return@doOnTextChanged
                }
                !mConstant.validateEmail(txtEmail.toString()) -> {
                    textInputLayoutEmail.helperText =
                        context.getString(R.string.error_email_invalid)
                }
                else -> {
                    textInputLayoutEmail.helperText = ""
                }
            }
        }

        password.doOnTextChanged { txtPassword, _, _, _ ->
            when {
                txtPassword!!.toString().isEmpty() -> {
                    textInputLayoutPassword.helperText = context.getText(R.string.erroremptyfield)
                    return@doOnTextChanged
                }
                !mConstant.validatelongitudepassword(txtPassword.toString()) -> {
                    textInputLayoutPassword.helperText =
                        context.getString(R.string.error_longitudepassword)
                }
                else -> {
                    textInputLayoutPassword.helperText = ""
                }
            }
        }
    }

    fun registerUserValidator(
        textInputLayoutFirsName: TextInputLayout,
        textFirstName: TextInputEditText,
        textInputLayoutLastName: TextInputLayout,
        textLastName: TextInputEditText,
        textInputLayoutEmail: TextInputLayout,
        textEmail: TextInputEditText,
        textInputLayoutPhone: TextInputLayout,
        textPhone: TextInputEditText,
        textInputLayoutPassword: TextInputLayout,
        textPassword: TextInputEditText,
        textInputLayoutConfirmPassword: TextInputLayout,
        textConfirmPassword: TextInputEditText,
        btnRegister: MaterialButton,
        context: Context,
        mConstant: Constant

    ) {
        textFirstName.doOnTextChanged { firstName, _, _, _ ->
            when {
                firstName.toString().isEmpty() -> {
                    textInputLayoutFirsName.helperText = context.getString(R.string.erroremptyfield)
                    btnRegister.isEnabled = false
                }
                !mConstant.validateonlyleter(firstName.toString()) -> {
                    textInputLayoutFirsName.helperText =
                        context.getString(R.string.error_firsname_onlyletter)
                    btnRegister.isEnabled = false
                }
                else -> {
                    textInputLayoutFirsName.helperText = ""
                    btnRegister.isEnabled = true
                }
            }
        }

        textLastName.doOnTextChanged { lastName, _, _, _ ->
            when {
                lastName.toString().isEmpty() -> {
                    textInputLayoutLastName.helperText = context.getString(R.string.erroremptyfield)
                    btnRegister.isEnabled = false
                }
                !mConstant.validateonlyleter(lastName.toString()) -> {
                    textInputLayoutLastName.helperText =
                        context.getString(R.string.error_lastname_onlyletter)
                    btnRegister.isEnabled = false
                }
                else -> {
                    textInputLayoutLastName.helperText = ""
                    btnRegister.isEnabled = true
                }
            }
        }

        textEmail.doOnTextChanged { txtEmail, _, _, _ ->
            when {
                txtEmail!!.toString().isEmpty() -> {
                    textInputLayoutEmail.helperText = context.getString(R.string.erroremptyfield)
                    btnRegister.isEnabled = false
                    return@doOnTextChanged
                }
                !mConstant.validateEmail(txtEmail.toString())!! -> {
                    textInputLayoutEmail.helperText =
                        context.getString(R.string.error_email_invalid)
                    btnRegister.isEnabled = false
                }
                else -> {
                    textInputLayoutEmail.helperText = ""
                    btnRegister.isEnabled = true
                }
            }
        }

        textPhone.doOnTextChanged { phone, _, _, _ ->
            when {
                phone.toString().isEmpty() -> {
                    textInputLayoutPhone.helperText = context.getString(R.string.erroremptyfield)
                    btnRegister.isEnabled = false
                }
                !mConstant.validatelenghnumberphone(phone.toString()) -> {
                    textInputLayoutPhone.helperText =
                        context.getString(R.string.error_longitud_number)
                    btnRegister.isEnabled = false
                }
                else -> {
                    textInputLayoutPhone.helperText = ""
                    btnRegister.isEnabled = true
                }
            }
        }

        textPassword.doOnTextChanged { password, _, _, _ ->
            when {
                password.toString().isEmpty() -> {
                    textInputLayoutPassword.helperText = context.getString(R.string.erroremptyfield)
                    btnRegister.isEnabled = false
                }
                !mConstant.validatelongitudepassword(password.toString()) -> {
                    textInputLayoutPassword.helperText =
                        context.getString(R.string.error_longitudepassword)
                    btnRegister.isEnabled = false
                }
                else -> {
                    textInputLayoutPassword.helperText = ""
                    btnRegister.isEnabled = true
                }
            }
        }

        textConfirmPassword.doOnTextChanged { password, _, _, _ ->
            when {
                password.toString().isEmpty() -> {
                    textInputLayoutConfirmPassword.helperText =
                        context.getString(R.string.erroremptyfield)
                    btnRegister.isEnabled = false
                }
                !mConstant.validatelongitudepassword(password.toString()) -> {
                    textInputLayoutConfirmPassword.helperText =
                        context.getString(R.string.error_longitudepassword)
                    btnRegister.isEnabled = false
                }
                textPassword.text.toString() != textConfirmPassword.text.toString() -> {
                    textInputLayoutConfirmPassword.helperText =
                        context.getString(R.string.error_no_coecidence_password)
                    btnRegister.isEnabled = false
                }
                else -> {
                    textInputLayoutConfirmPassword.helperText = ""
                    btnRegister.isEnabled = true
                }
            }
        }
    }
}