package com.tikonsil.tikonsil509admin.ui.fragment.registerCost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.data.remote.provider.CostInnoveritProvider
import com.tikonsil.tikonsil509admin.databinding.FragmentRegisterCostInnoveritBinding
import com.tikonsil.tikonsil509admin.domain.model.CostInnoverit
import com.tikonsil.tikonsil509admin.presentation.costInnoverit.PriceCostInnoveritViewModel
import com.tikonsil.tikonsil509admin.ui.fragment.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class RegisterCostInnoveritFragment:BaseFragment<FragmentRegisterCostInnoveritBinding,PriceCostInnoveritViewModel>() {


    private var countrySelected:String?=null
    private val priceCostViewModel:PriceCostInnoveritViewModel by viewModels()
    @Inject
     lateinit var costInnoveritProvider: CostInnoveritProvider

    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)
        val bottomNavigationView =
            activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.visibility = View.GONE

        observeViewModel()
        costInnoveritProvider = CostInnoveritProvider()
        manageCountrySelected()
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterCostInnoveritBinding  =FragmentRegisterCostInnoveritBinding.inflate(inflater,container,false)

    override fun getViewModel() =PriceCostInnoveritViewModel::class.java

    private fun manageCountrySelected() {
        countrySelected = binding.buttonpaises.selectedCountryNameCode
        binding.buttonpaises.setOnCountryChangeListener{
            countrySelected = binding.buttonpaises.selectedCountryNameCode
        }
    }

    private fun observeViewModel() {
        binding.apply {
            btnSend.setOnClickListener {
             saveData()
            }
        }

    }

    private fun saveData() {
        binding.apply {
            if (buttonpaises.selectedCountryNameCode.toString().isNotEmpty() && operatorName.text.toString().isNotEmpty() && saleprice.text.toString().isNotEmpty() && codeproduct.text.toString().isNotEmpty() && monyey.text.toString().isNotEmpty()){
               val  formatSales = "${salekliyan.text.toString()} ${monyey.text.toString().toUpperCase()} ==> ${saleprice.text.toString()} USD ==> ${operatorName.text.toString().toUpperCase()}"
                val saved =CostInnoverit(salekliyan.text.toString(),operatorName.text.toString().toUpperCase(),saleprice.text.toString(),monyey.text.toString().toUpperCase(),"USD",codeproduct.text.toString(),countrySelected!!,formatSales,"")
                priceCostViewModel.registerPriceCost(saved)
                priceCostViewModel.responsePriceCost.observe(viewLifecycleOwner, Observer { response->
                    if (response.isSuccessful){
                        Toast.makeText(requireContext() , "se registro correctamente " , Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(requireContext() , "Error al registrar ${response.errorBody()}" , Toast.LENGTH_SHORT).show()
                    }
                })
            }else{
                layoutkliyan.helperText = "ranpli tout kasye yo"
            }
        }
    }
}