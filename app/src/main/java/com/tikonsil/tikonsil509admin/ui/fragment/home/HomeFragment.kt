package com.tikonsil.tikonsil509admin.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.tikonsil.tikonsil509admin.databinding.FragmentHomeBinding
import com.tikonsil.tikonsil509admin.presentation.home.UserViewModel


class HomeFragment : HomeValidate<FragmentHomeBinding, UserViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
        super.onViewCreated(view, savedInstanceState)
        showDataInView()
        observeData()
        observedatatotaluser()
        observedatatotalsales()
        observeNotificationCount()
        generateToken()
        txtcount.setOnClickListener {
            observerListNotification()
        }

    }

    private fun generateToken() {
        mTokenProvider.createToken(mAuthProvider.getId().toString())
    }

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) =FragmentHomeBinding.inflate(inflater,container,false)
    override fun getViewModel() = UserViewModel::class.java


}