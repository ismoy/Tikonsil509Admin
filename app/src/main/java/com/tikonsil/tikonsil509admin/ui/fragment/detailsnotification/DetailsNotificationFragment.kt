package com.tikonsil.tikonsil509admin.ui.fragment.detailsnotification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tikonsil.tikonsil509admin.databinding.FragmentDetailsNotificationBinding
import com.tikonsil.tikonsil509admin.domain.model.NotificationList


class DetailsNotificationFragment : Fragment() {
  private lateinit var binding: FragmentDetailsNotificationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =FragmentDetailsNotificationBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}