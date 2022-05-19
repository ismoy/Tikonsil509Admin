package com.tikonsil.tikonsil509admin.ui.fragment.historysales

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.databinding.FragmentHistorySalesBinding
import com.tikonsil.tikonsil509admin.presentation.historysales.HistorySalesViewModel


class HistorySalesFragment : ValidateSaleHistory<FragmentHistorySalesBinding,HistorySalesViewModel>(){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // shimmerFrameLayout.startShimmer()
        observehistorysales()
    }

    override fun getViewModel() =HistorySalesViewModel::class.java

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?)= FragmentHistorySalesBinding.inflate(inflater,container,false)


}