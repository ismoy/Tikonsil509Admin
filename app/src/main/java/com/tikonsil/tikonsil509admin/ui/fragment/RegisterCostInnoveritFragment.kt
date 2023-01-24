package com.tikonsil.tikonsil509admin.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.tikonsil.tikonsil509admin.data.remote.provider.CostInnoveritProvider
import com.tikonsil.tikonsil509admin.data.remote.provider.IdProductProvider
import com.tikonsil.tikonsil509admin.databinding.FragmentRegisterCostInnoveritBinding
import com.tikonsil.tikonsil509admin.domain.model.CostInnoverit
import com.tikonsil.tikonsil509admin.domain.model.IdProducts
import com.tikonsil.tikonsil509admin.presentation.costInnoverit.PriceCostInnoveritViewModel


class RegisterCostInnoveritFragment : Fragment() {

    private lateinit var binding:FragmentRegisterCostInnoveritBinding

    private lateinit var viewModel: PriceCostInnoveritViewModel

    private lateinit var costInnoveritProvider: CostInnoveritProvider

    private lateinit var iproductProvider: IdProductProvider

    private var countrySelected:String?=null

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterCostInnoveritBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)
        viewModel = PriceCostInnoveritViewModel()

        observeViewModel()
        costInnoveritProvider = CostInnoveritProvider()
        iproductProvider= IdProductProvider()
        manageCountrySelected()
    }

    private fun manageCountrySelected() {
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
                val saved =CostInnoverit(salekliyan.text.toString(),operatorName.text.toString().toUpperCase(),saleprice.text.toString(),monyey.text.toString().toUpperCase(),"USD",codeproduct.text.toString(),countrySelected!!,formatSales)
                viewModel.registerPriceCost(saved)
                viewModel.responsePriceCost.observe(viewLifecycleOwner, Observer { response->
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