package com.tikonsil.tikonsil509admin.ui.fragment.countryprice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.databinding.FragmentCountryPriceBinding
import com.tikonsil.tikonsil509admin.presentation.countryprice.CountryPriceViewModel


class CountryPriceFragment : ValidateCountryPrice<FragmentCountryPriceBinding,CountryPriceViewModel> (){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        observeDataCountryPrice()
        validateRealtime()
        btn_update_price.setOnClickListener {
            updateData()
        }
    }
    override fun getViewModel()=CountryPriceViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentCountryPriceBinding.inflate(inflater,container,false)


}