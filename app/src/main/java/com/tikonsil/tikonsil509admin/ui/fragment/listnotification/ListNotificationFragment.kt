package com.tikonsil.tikonsil509admin.ui.fragment.listnotification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tikonsil.tikonsil509admin.databinding.FragmentListNotificationBinding
import com.tikonsil.tikonsil509admin.presentation.listnotification.ListNotificationViewModel

class ListNotificationFragment :ListNotificationValidate<FragmentListNotificationBinding,ListNotificationViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observelistnotification()

    }

    override fun getViewModel()=ListNotificationViewModel::class.java

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?)= FragmentListNotificationBinding.inflate(inflater,container,false)

}