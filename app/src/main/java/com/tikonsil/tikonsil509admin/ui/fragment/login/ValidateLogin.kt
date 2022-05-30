package com.tikonsil.tikonsil509.ui.fragment.login

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.tikonsil.tikonsil509admin.data.remote.provider.AuthProvider
import com.tikonsil.tikonsil509admin.presentation.login.LoginViewModelFactory
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.domain.repository.login.LoginRepository
import com.tikonsil.tikonsil509admin.presentation.login.LoginViewModel
import com.tikonsil.tikonsil509admin.ui.activity.home.HomeActivity
import com.tikonsil.tikonsil509admin.utils.Constant

/** * Created by ISMOY BELIZAIRE on 23/04/2022. */
abstract class ValidateLogin<VM:ViewModel,VB:ViewBinding>:Fragment() {
 protected lateinit var binding:VB
 protected lateinit var mConstant: Constant
 protected lateinit var viewmodel: LoginViewModel
 protected lateinit var mAuthProvider: AuthProvider
 lateinit var dialog:Dialog
 protected lateinit var navController: NavController

 override fun onCreateView(
  inflater: LayoutInflater,
  container: ViewGroup?,
  savedInstanceState: Bundle?
 ): View? {
  binding = getFragmentBinding(inflater, container)
  val repository = LoginRepository()
  val factory = LoginViewModelFactory(repository)
  viewmodel = ViewModelProvider(requireActivity(),factory)[LoginViewModel::class.java]
  mAuthProvider = AuthProvider()
  mConstant = Constant()
  dialog = Dialog(requireContext())
  return binding.root
 }

 fun validateRealTime(){
  binding.root.apply {
   findViewById<TextInputEditText>(R.id.emaillogin).addTextChangedListener(object :TextWatcher{
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
     when{
      findViewById<TextInputEditText>(R.id.emaillogin).text.toString().isEmpty()->{
       findViewById<TextInputLayout>(R.id.layoutemaillogin).helperText = getString(R.string.erroremptyfield)
      }
      !mConstant.validateEmail(findViewById<TextInputEditText>(R.id.emaillogin).text.toString())->{
      findViewById<TextInputLayout>(R.id.layoutemaillogin).helperText = getString(R.string.error_email_invalid)
     }else->{
      findViewById<TextInputLayout>(R.id.layoutemaillogin).helperText = ""
     }
     }
    }
    override fun afterTextChanged(p0: Editable?) {
    }

   })
   //validate password
   findViewById<TextInputEditText>(R.id.passwordlogin).addTextChangedListener(object :TextWatcher{
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
     when{
      findViewById<TextInputEditText>(R.id.passwordlogin).text.toString().isEmpty()->{
       findViewById<TextInputLayout>(R.id.layoutpasswordlogin).helperText = getString(R.string.erroremptyfield)
      }
      !mConstant.validatelongitudepassword(findViewById<TextInputEditText>(R.id.passwordlogin).text.toString())->{
       findViewById<TextInputLayout>(R.id.layoutpasswordlogin).helperText =getString(R.string.error_longitudepassword)
      }else->{
      findViewById<TextInputLayout>(R.id.layoutpasswordlogin).helperText=""
     }
     }
    }
    override fun afterTextChanged(p0: Editable?) {
    }

   })

  }
 }
 fun validateOnclickButton(){
  binding.root.apply {
   //Validate Email
   when{
    findViewById<TextInputEditText>(R.id.emaillogin).text.toString().isEmpty()->{
     findViewById<TextInputLayout>(R.id.layoutemaillogin).helperText = getString(R.string.erroremptyfield)
    }
    !mConstant.validateEmail(findViewById<TextInputEditText>(R.id.emaillogin).text.toString())->{
     findViewById<TextInputLayout>(R.id.layoutemaillogin).helperText = getString(R.string.error_email_invalid)
    }else->{
    findViewById<TextInputLayout>(R.id.layoutemaillogin).helperText = ""
   }
   }
   //Validate Password
   when{
    findViewById<TextInputEditText>(R.id.passwordlogin).text.toString().isEmpty()->{
     findViewById<TextInputLayout>(R.id.layoutpasswordlogin).helperText = getString(R.string.erroremptyfield)
    }
    !mConstant.validatelongitudepassword(findViewById<TextInputEditText>(R.id.passwordlogin).text.toString())->{
     findViewById<TextInputLayout>(R.id.layoutpasswordlogin).helperText =getString(R.string.error_longitudepassword)
    }else->{
    findViewById<TextInputLayout>(R.id.layoutpasswordlogin).helperText=""
     Login(findViewById<TextInputEditText>(R.id.emaillogin).text.toString(),findViewById<TextInputEditText>(R.id.passwordlogin).text.toString())

   }
   }
  }
 }

  private fun  Login(email:String?, password:String?) {
   dialog.setContentView(R.layout.dialog_loading)
   dialog.setCancelable(false)
   if (dialog.window!=null){
    dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
   }
   dialog.show()
   viewmodel.login(email!!,password!!)
   viewmodel.responseLoginRepository.observe(requireActivity(), Observer { it ->
    it.addOnCompleteListener {
     if (it.isSuccessful){
      if (mAuthProvider.isEmailVerified()==true){
       dialog.dismiss()
       val intent: Intent? = Intent(requireContext(), HomeActivity::class.java)
       intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
       startActivity(intent)
       }else{
        dialog.dismiss()
        Toast.makeText(requireContext(), "Por favor verifica su cuenta", Toast.LENGTH_SHORT).show()
       }
      }else{
       Toast.makeText(requireContext(), it.exception?.message, Toast.LENGTH_SHORT).show()
      dialog.dismiss()
      }
     }
   })
 }

 abstract fun getViewModel():Class<VM>
 abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?):VB
}