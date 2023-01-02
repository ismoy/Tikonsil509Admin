package com.tikonsil.tikonsil509admin.ui.fragment.changeStatusSale

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.databinding.FragmentChangeStatusSaleBinding

class ChangeStatusSaleFragment : Fragment() {

    private lateinit var binding:FragmentChangeStatusSaleBinding

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeStatusSaleBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)
    }
}