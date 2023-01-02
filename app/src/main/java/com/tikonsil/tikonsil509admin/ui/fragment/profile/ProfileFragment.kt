package com.tikonsil.tikonsil509admin.ui.fragment.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.tikonsil.tikonsil509admin.presentation.profile.ProfileViewModel
import com.tikonsil.tikonsil509admin.databinding.FragmentProfileBinding


class ProfileFragment : ProfileInfo<FragmentProfileBinding, ProfileViewModel>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showDataInProfile()
        Logout()
        imageView.setOnClickListener {
            openGallery()
        }
    }

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentProfileBinding.inflate(inflater,container,false)
    override fun getViewModel() = ProfileViewModel::class.java


}