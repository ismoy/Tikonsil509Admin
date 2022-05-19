package com.tikonsil.tikonsil509admin.ui.fragment.register

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.tikonsil.tikonsil509.presentation.register.RegisterViewModel
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.databinding.FragmentRegisterBinding
import kotlinx.coroutines.launch

class RegisterFragment: ValidateRegister<RegisterViewModel, FragmentRegisterBinding>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       ValidateItemRealTime()
        binding.btnRegistrar.setOnClickListener {
            isNetworkAvailable(requireContext())
        }
        navController = Navigation.findNavController(view)
        binding.arrowbackregister.setOnClickListener {
            navController.navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }



    @SuppressLint("MissingPermission")
    private  fun isNetworkAvailable(context: Context):Boolean {
        val connectivityManager =context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        } else {
            TODO("VERSION.SDK_INT < M")
        }
        if (capabilities !=null){
            when{
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)->{
                    viewmodel.viewModelScope.launch {
                        clicktoregister(binding.email.text.toString(),binding.password.text.toString())
                    }
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)->{
                    viewmodel.viewModelScope.launch {
                        clicktoregister(binding.email.text.toString(),binding.password.text.toString())

                    }
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE)->{
                    viewmodel.viewModelScope.launch {
                        clicktoregister(binding.email.text.toString(),binding.password.text.toString())

                    }
                    return true
                }
            }
        }
        Toast.makeText(requireContext(), getString(R.string.noconnetion_internet), Toast.LENGTH_SHORT).show()
        return false
    }

    override fun getViewModel()= RegisterViewModel::class.java
    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?)=FragmentRegisterBinding.inflate(inflater,container,false)

}