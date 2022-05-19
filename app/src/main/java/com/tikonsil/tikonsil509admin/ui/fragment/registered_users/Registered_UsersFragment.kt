package com.tikonsil.tikonsil509admin.ui.fragment.registered_users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tikonsil.tikonsil509admin.databinding.FragmentRegisteredUsersBinding
import com.tikonsil.tikonsil509admin.presentation.registereduser.RegisteredUserViewModel


class Registered_UsersFragment : RegistreredUserValidate<RegisteredUserViewModel,FragmentRegisteredUsersBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }




    override fun getViewModel() =RegisteredUserViewModel::class.java
    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?)= FragmentRegisteredUsersBinding.inflate(inflater,container,false)


}