package com.tikonsil.tikonsil509admin.ui.fragment.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.data.remote.provider.AuthProvider
import com.tikonsil.tikonsil509admin.databinding.FragmentLoginBinding
import com.tikonsil.tikonsil509admin.presentation.login.LoginViewModel
import com.tikonsil.tikonsil509admin.ui.activity.home.HomeActivity
import com.tikonsil.tikonsil509admin.ui.fragment.base.BaseFragment
import com.tikonsil.tikonsil509admin.utils.UtilsView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment :BaseFragment<FragmentLoginBinding,LoginViewModel>() {

    @Inject
    lateinit var mAuthProvider:AuthProvider
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        mAuthProvider = AuthProvider()
        validateRealTime()
        binding.iralregistro.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.btnLogin.setOnClickListener {
            loginUser()
        }
        viewModelObserver()
    }

    private fun loginUser() {
        with(binding){
            when{
                emaillogin.text.toString().isNotEmpty() && passwordlogin.text.toString().isNotEmpty() ->{
                    login(emaillogin.text.toString(),binding.passwordlogin.text.toString())
                }
                else -> {
                    layoutpasswordlogin.helperText = context?.getString(R.string.erroremptyfield)
                }
            }

        }
    }

    private fun login(email: String, password: String) {
      loginViewModel.login(email, password)
    }

    private fun viewModelObserver() {
        loginViewModel.responseLogin.observe(viewLifecycleOwner){task->
            task.addOnCompleteListener {
                if (it.isSuccessful){
                    if (mAuthProvider.isEmailVerified() == true){
                        UtilsView.hideProgress(binding.btnLogin,binding.progressBar,requireActivity().getString(R.string.singin))
                        val intent = Intent(requireContext(),HomeActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK  or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                    }else{
                        Toast.makeText(
                            requireContext() ,
                            getString(R.string.verifyaccount) ,
                            Toast.LENGTH_LONG
                        ).show()
                        UtilsView.hideProgress(binding.btnLogin,binding.progressBar,requireActivity().getString(R.string.singin))
                    }
                }else{
                    Toast.makeText(requireContext() , it.exception?.message , Toast.LENGTH_LONG)
                        .show()
                    UtilsView.hideProgress(binding.btnLogin,binding.progressBar,requireActivity().getString(R.string.singin))
                }
            }
        }

        loginViewModel.loading.observe(viewLifecycleOwner){
            UtilsView.showProgress(binding.btnLogin,binding.progressBar)
        }
    }

    private fun validateRealTime() {
        with(binding){
            UtilsView.loginValidator(layoutemaillogin,emaillogin,layoutpasswordlogin,passwordlogin,
            requireContext(),mConstant)
        }
    }

    override fun getViewModel()= LoginViewModel::class.java

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?)= FragmentLoginBinding.inflate(inflater,container,false)

}