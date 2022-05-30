package com.tikonsil.tikonsil509admin.ui.fragment.register

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.viewbinding.ViewBinding
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.hbb20.CountryCodePicker
import com.tikonsil.tikonsil509admin.data.remote.provider.AuthProvider
import com.tikonsil.tikonsil509.domain.model.Users
import com.tikonsil.tikonsil509.domain.repository.register.RegisterRepository
import com.tikonsil.tikonsil509.presentation.register.RegisterViewModel
import com.tikonsil.tikonsil509.presentation.register.RegisterViewModelFactory
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.utils.Constant

/** * Created by ISMOY BELIZAIRE on 23/04/2022. */
 abstract class ValidateRegister<VM: ViewModel,V: ViewBinding>:Fragment() {
 protected lateinit var binding:V
 protected lateinit var mConstant: Constant
 protected lateinit var viewmodel: RegisterViewModel
 protected lateinit var mAuthProvider: AuthProvider
 lateinit var dialog:Dialog
 protected lateinit var auth: FirebaseAuth
 protected lateinit var navController: NavController

 override fun onCreateView(
  inflater: LayoutInflater,
  container: ViewGroup?,
  savedInstanceState: Bundle?
 ): View? {
  binding = getFragmentBinding(inflater, container)
  val repository = RegisterRepository(requireActivity().application)
  val factory = RegisterViewModelFactory(repository)
  viewmodel = ViewModelProvider(requireActivity(),factory)[RegisterViewModel::class.java]
  mAuthProvider = AuthProvider()
  mConstant = Constant()
  dialog = Dialog(requireContext())
  auth = FirebaseAuth.getInstance()
  return binding.root
 }

  fun ValidateItemRealTime() {
  binding.root.apply {
   //validate Firstname
   findViewById<TextInputEditText>(R.id.firstname).addTextChangedListener(object: TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
     when {
     findViewById<TextInputEditText>(R.id.firstname).text.toString().isEmpty() -> {
      findViewById<TextInputLayout>(R.id.layoutfirstname).helperText = getString(R.string.erroremptyfield)
      }
      !mConstant.validateonlyleter(findViewById<TextInputEditText>(R.id.firstname).text.toString())->{
       findViewById<TextInputLayout>(R.id.layoutfirstname).helperText = getString(R.string.error_firsname_onlyletter)
      }else->{
      findViewById<TextInputLayout>(R.id.layoutfirstname).helperText=""
     }
     }
    }
    override fun afterTextChanged(p0: Editable?) {
    }

   })

   //Validate LastName
   findViewById<TextInputEditText>(R.id.lastname).addTextChangedListener(object: TextWatcher{
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
     when{
      findViewById<TextInputEditText>(R.id.lastname).text.toString().isEmpty()->{
       findViewById<TextInputLayout>(R.id.layoutlastname).helperText = getString(R.string.erroremptyfield)
      }
      !mConstant.validateonlyleter(findViewById<TextInputEditText>(R.id.lastname).text.toString())->{
       findViewById<TextInputLayout>(R.id.layoutlastname).helperText = getString(R.string.error_lastname_onlyletter)
      }else->{
       findViewById<TextInputLayout>(R.id.layoutlastname).helperText = ""
      }
     }
    }
    override fun afterTextChanged(p0: Editable?) {
    }
   })
   //validate email
    findViewById<TextInputEditText>(R.id.email).addTextChangedListener(object: TextWatcher{
     override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
     }
     override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
      when{
       findViewById<TextInputEditText>(R.id.email).text.toString().isEmpty()->{
        findViewById<TextInputLayout>(R.id.layoutemail).helperText = getString(R.string.erroremptyfield)
       }
       !mConstant.validateEmail(findViewById<TextInputEditText>(R.id.email).text.toString())->{
        findViewById<TextInputLayout>(R.id.layoutemail).helperText = getString(R.string.error_email_invalid)
       }else->{
        findViewById<TextInputLayout>(R.id.layoutemail).helperText = ""
       }
      }
     }
     override fun afterTextChanged(p0: Editable?) {
     }

    })
   //validate country code
   findViewById<TextInputEditText>(R.id.phone).addTextChangedListener(object : TextWatcher{
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
    @SuppressLint("CutPasteId")
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
     when{
      findViewById<TextInputEditText>(R.id.phone).text.toString().isEmpty()->{
       findViewById<TextInputLayout>(R.id.layoutphone).helperText =getString(R.string.erroremptyfield)
      }
      !mConstant.validatelenghnumberphone(findViewById<TextInputEditText>(R.id.phone).text.toString())->{
       findViewById<TextInputLayout>(R.id.layoutphone).helperText = getString(R.string.error_longitud_number)
      }else->{
      findViewById<TextInputLayout>(R.id.layoutphone).hint = findViewById<TextInputEditText>(R.id.phone).text.toString()
       findViewById<TextInputLayout>(R.id.layoutphone).helperText = ""
      }
     }
    }
    override fun afterTextChanged(p0: Editable?) {
    }
   })
   //validate password
   findViewById<TextInputEditText>(R.id.password).addTextChangedListener(object :TextWatcher{
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
     when{
      findViewById<TextInputEditText>(R.id.password).text.toString().isEmpty()->{
       findViewById<TextInputLayout>(R.id.layoutpassword).helperText = getString(R.string.erroremptyfield)
      }
      !mConstant.validatelongitudepassword(findViewById<TextInputEditText>(R.id.password).text.toString())->{
       findViewById<TextInputLayout>(R.id.layoutpassword).helperText =getString(R.string.error_longitudepassword)
      }else->{
      findViewById<TextInputLayout>(R.id.layoutpassword).helperText=""
     }
     }
    }
    override fun afterTextChanged(p0: Editable?) {
    }

   })

   //validate password
   findViewById<TextInputEditText>(R.id.repeatpassword).addTextChangedListener(object :TextWatcher{
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
    @SuppressLint("CutPasteId")
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
     when{
      findViewById<TextInputEditText>(R.id.repeatpassword).text.toString().isEmpty()->{
       findViewById<TextInputLayout>(R.id.layoutconfirmpassword).helperText = getString(R.string.erroremptyfield)
      }
      !mConstant.validatelongitudepassword(findViewById<TextInputEditText>(R.id.repeatpassword).text.toString())->{
       findViewById<TextInputLayout>(R.id.layoutconfirmpassword).helperText =getString(R.string.error_longitudepassword)
      }
      findViewById<TextInputEditText>(R.id.password).text.toString() != findViewById<TextInputEditText>(R.id.repeatpassword).text.toString()->{
       findViewById<TextInputLayout>(R.id.layoutconfirmpassword).helperText=getString(R.string.error_no_coecidence_password)
      }
      else->{
       findViewById<TextInputLayout>(R.id.layoutconfirmpassword).helperText=""
       findViewById<Button>(R.id.btn_registrar).isEnabled=true
       findViewById<Button>(R.id.btn_registrar).isClickable= true
     }

     }
    }
    override fun afterTextChanged(p0: Editable?) {
    }

   })
  }
 }
 @SuppressLint("CutPasteId")
 fun validateonclickbutton(){
  binding.root .apply {
   val pasisf =  findViewById<CountryCodePicker>(R.id.buttonpaises).selectedCountryNameCode
   //validate CountryName
   when{

    findViewById<CountryCodePicker>(R.id.buttonpaises).selectedCountryNameCode.isEmpty()->{
     Toast.makeText(requireContext(), "requerido", Toast.LENGTH_SHORT).show()
    }else->{
     Log.d("pasisseleccionado",""+pasisf)
    }
   }
   //validate firstname
   when{
    findViewById<TextInputEditText>(R.id.firstname).text.toString().isEmpty()->{
     findViewById<TextInputLayout>(R.id.layoutfirstname).helperText = getString(R.string.erroremptyfield)
    }
    !mConstant.validateonlyleter(findViewById<TextInputEditText>(R.id.firstname).text.toString())->{
     findViewById<TextInputLayout>(R.id.layoutfirstname).helperText = getString(R.string.error_firsname_onlyletter)
    }else->{
    findViewById<TextInputLayout>(R.id.layoutfirstname).helperText=""
   }
   }
   //validate lastName
   when{
    findViewById<TextInputEditText>(R.id.lastname).text.toString().isEmpty()->{
     findViewById<TextInputLayout>(R.id.layoutlastname).helperText = getString(R.string.erroremptyfield)
    }
    !mConstant.validateonlyleter(findViewById<TextInputEditText>(R.id.lastname).text.toString())->{
     findViewById<TextInputLayout>(R.id.layoutlastname).helperText = getString(R.string.error_lastname_onlyletter)
    }else->{
    findViewById<TextInputLayout>(R.id.layoutlastname).helperText = ""
   }
   }
   //Validate Email
   when{
    findViewById<TextInputEditText>(R.id.email).text.toString().isEmpty()->{
     findViewById<TextInputLayout>(R.id.layoutemail).helperText = getString(R.string.erroremptyfield)
    }
    !mConstant.validateEmail(findViewById<TextInputEditText>(R.id.email).text.toString())->{
     findViewById<TextInputLayout>(R.id.layoutemail).helperText = getString(R.string.error_email_invalid)
    }else->{
    findViewById<TextInputLayout>(R.id.layoutemail).helperText = ""
   }
   }
   //Validate Country Code
   when{
    findViewById<TextInputEditText>(R.id.phone).text.toString().isEmpty()->{
     findViewById<TextInputLayout>(R.id.layoutphone).helperText =getString(R.string.erroremptyfield)
    }
    !mConstant.validatelenghnumberphone(findViewById<TextInputEditText>(R.id.phone).text.toString())->{
     findViewById<TextInputLayout>(R.id.layoutphone).helperText = getString(R.string.error_longitud_number)
    }else->{
    findViewById<TextInputLayout>(R.id.layoutphone).hint = findViewById<TextInputEditText>(R.id.phone).text.toString()
    findViewById<TextInputLayout>(R.id.layoutphone).helperText = ""
   }
   }
   //Validate Password
   when{
    findViewById<TextInputEditText>(R.id.password).text.toString().isEmpty()->{
     findViewById<TextInputLayout>(R.id.layoutpassword).helperText = getString(R.string.erroremptyfield)
    }
    !mConstant.validatelongitudepassword(findViewById<TextInputEditText>(R.id.password).text.toString())->{
     findViewById<TextInputLayout>(R.id.layoutpassword).helperText =getString(R.string.error_longitudepassword)
    }else->{
    findViewById<TextInputLayout>(R.id.layoutpassword).helperText=""
   }
   }
   //Validate Confirm Password
   when{
    findViewById<TextInputEditText>(R.id.repeatpassword).text.toString().isEmpty()->{
     findViewById<TextInputLayout>(R.id.layoutconfirmpassword).helperText = getString(R.string.erroremptyfield)
    }
    !mConstant.validatelongitudepassword(findViewById<TextInputEditText>(R.id.repeatpassword).text.toString())->{
     findViewById<TextInputLayout>(R.id.layoutconfirmpassword).helperText =getString(R.string.error_longitudepassword)
    }
    findViewById<TextInputEditText>(R.id.password).text.toString() != findViewById<TextInputEditText>(R.id.repeatpassword).text.toString()->{
     findViewById<TextInputLayout>(R.id.layoutconfirmpassword).helperText=getString(R.string.error_no_coecidence_password)
    }
    else-> {
     val users = Users()
     findViewById<TextInputLayout>(R.id.layoutconfirmpassword).helperText = ""
     findViewById<Button>(R.id.btn_registrar).isEnabled = true
     findViewById<Button>(R.id.btn_registrar).isClickable = true
     SaveData(findViewById<CountryCodePicker>(R.id.buttonpaises).selectedCountryName.toString(),
      findViewById<CountryCodePicker>(R.id.buttonpaises).selectedCountryNameCode.toString(),
      findViewById<TextInputEditText>(R.id.firstname).text.toString(),
      findViewById<TextInputEditText>(R.id.lastname).text.toString(),
      findViewById<TextInputEditText>(R.id.email).text.toString(),
      findViewById<CountryCodePicker>(R.id.codigo).selectedCountryCodeWithPlus + findViewById<TextInputEditText>(R.id.phone).text.toString(),
      users.role,
      findViewById<TextInputEditText>(R.id.password).text.toString(),
      findViewById<TextInputEditText>(R.id.repeatpassword).text.toString(),users.status,"")
     }
    }
   }
  }


 private fun SaveData(countryselected:String?, coutrycode:String?, firstname:String?, lastname:String?, email:String?, phone:String?, role:Int, password:String?, confirmpassword:String?, status:Int?, image:String?) {
  val users =Users(mAuthProvider.getId().toString(),countryselected,coutrycode,firstname,lastname,email,phone,role,password,confirmpassword,
  status!!,image)
  dialog.setContentView(R.layout.dialog_loading)
  dialog.setCancelable(false)
  if (dialog.window!=null){
   dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
  }
  dialog.show()
  viewmodel.register(mAuthProvider.getId().toString(),users)
  viewmodel.responseRegister.observe(requireActivity(), Observer {
   if (it.isSuccessful){
    dialog.dismiss()
   }else{
    Toast.makeText(requireContext(), it.errorBody().toString(), Toast.LENGTH_SHORT).show()
   }
  })
 }

 suspend fun clicktoregister(email: String?, password: String?){
  mAuthProvider.register(email, password).addOnCompleteListener { task->
   if (task.isSuccessful){
    validateonclickbutton()
    val user:FirebaseUser? = auth.currentUser
    user?.sendEmailVerification()?.addOnCompleteListener { task1:Task<Void?>?->
     if (task1?.isSuccessful!!){
      Toast.makeText(requireContext(), getString(R.string.succescreateaccount), Toast.LENGTH_SHORT).show()
     }
    }?.addOnFailureListener { e:Exception->

    }
    mAuthProvider.lenguaje()
   }else{
    val inputemail =binding.root.findViewById<TextInputLayout>(R.id.layoutemail)
    inputemail.helperText = getString(R.string.existemail)
   }
  }
 }

 abstract fun getViewModel():Class<VM>
 abstract fun getFragmentBinding(inflater: LayoutInflater,container: ViewGroup?):V

}