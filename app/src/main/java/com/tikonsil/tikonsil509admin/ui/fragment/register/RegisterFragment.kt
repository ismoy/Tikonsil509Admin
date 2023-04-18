package com.tikonsil.tikonsil509admin.ui.fragment.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.data.remote.provider.AuthProvider
import com.tikonsil.tikonsil509admin.databinding.FragmentRegisterBinding
import com.tikonsil.tikonsil509admin.domain.model.Users
import com.tikonsil.tikonsil509admin.presentation.home.UserViewModel
import com.tikonsil.tikonsil509admin.ui.fragment.base.BaseFragment
import com.tikonsil.tikonsil509admin.utils.UtilsView.hideProgress
import com.tikonsil.tikonsil509admin.utils.UtilsView.registerUserValidator
import com.tikonsil.tikonsil509admin.utils.UtilsView.showProgress
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment: BaseFragment<FragmentRegisterBinding,UserViewModel>() {

    private var codeCountrySelected: String? = null
    private var countrySelected: String? = null
    private lateinit var auth: FirebaseAuth
    private  val userViewModel:UserViewModel by viewModels()
    @Inject
    lateinit var mAuthProvider:AuthProvider

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuthProvider = AuthProvider()
        auth = FirebaseAuth.getInstance()
        navController = Navigation.findNavController(view)
        binding.arrowbackregister.setOnClickListener {
            navController.navigate(R.id.action_registerFragment_to_loginFragment)
        }
        with(binding) {
            registerUserValidator(layoutfirstname,firstname,layoutlastname,lastname,layoutemail,email,
                layoutphone,phone,layoutpassword,password,layoutconfirmpassword,repeatpassword,btnRegistrar,
                requireContext(),mConstant)
        }
        setCountryListener()
        binding.btnRegistrar.setOnClickListener(onClickCreateUser())
        onViewModelObserver()

    }


    private fun onViewModelObserver() {
        userViewModel.isLoading.observe(viewLifecycleOwner){
            showProgress(binding.btnRegistrar,binding.progressBar)
        }
        userViewModel.registerUserResponse.observe(viewLifecycleOwner){response->
            if (response.isSuccessful){
                hideProgress(binding.btnRegistrar,binding.progressBar,requireActivity().getString(R.string.register))
            }else{
                Toast.makeText(requireContext(), response.errorBody().toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun onClickCreateUser(): View.OnClickListener {
        return View.OnClickListener {
            with(binding) {
                if (firstname.text.toString().isNotEmpty() && lastname.text.toString().isNotEmpty() &&
                    email.text.toString().isNotEmpty() && phone.text.toString().isNotEmpty() &&
                    password.text.toString().isNotEmpty() && repeatpassword.text.toString().isNotEmpty()){
                    showProgress(binding.btnRegistrar,binding.progressBar)
                    mAuthProvider.register(email.text.toString(),repeatpassword.text.toString()).addOnCompleteListener {task->
                        if (task.isSuccessful){
                            sendUserDataInRealTimeDataBase()
                            val user: FirebaseUser? = auth.currentUser
                            user?.sendEmailVerification()?.addOnCompleteListener { task1: Task<Void?>?->
                                if (task1?.isSuccessful!!){
                                    Toast.makeText(requireContext(), getString(R.string.succescreateaccount), Toast.LENGTH_SHORT).show()
                                    navController.navigate(R.id.action_registerFragment_to_loginFragment)
                                }
                            }?.addOnFailureListener { e:Exception->

                            }
                            mAuthProvider.language(requireContext())
                        }else{
                            layoutemail.helperText = getString(R.string.existemail)
                        }
                    }

                }else{
                    Toast.makeText(requireContext(), getString(R.string.erroremptyfield), Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun sendUserDataInRealTimeDataBase() {
        with(binding) {
            val users = Users(mAuthProvider.getId().toString(),countrySelected,
                firstname.text.toString(),lastname.text.toString(),email.text.toString(),"${binding.codigo.text}${binding.phone.text.toString()}",3,repeatpassword.text.toString(),
            1,0.0,0.0,0.0,0.0,codeCountrySelected,null)
            userViewModel.register(mAuthProvider.getId().toString(),users)
        }
    }
    private fun setCountryListener() {
        with(binding) {
            codeCountrySelected = buttonpaises.selectedCountryNameCode
            countrySelected = buttonpaises.selectedCountryName
            codigo.text = buttonpaises.selectedCountryCodeWithPlus
            buttonpaises.setOnCountryChangeListener {
                codeCountrySelected = buttonpaises.selectedCountryNameCode
                countrySelected = buttonpaises.selectedCountryName
                codigo.text = buttonpaises.selectedCountryCodeWithPlus
            }

        }

    }
    override fun getViewModel()= UserViewModel::class.java
    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?)=FragmentRegisterBinding.inflate(inflater,container,false)

}