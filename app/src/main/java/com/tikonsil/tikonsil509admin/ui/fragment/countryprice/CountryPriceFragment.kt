package com.tikonsil.tikonsil509admin.ui.fragment.countryprice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.databinding.BottomSheetUpdateCountryPriceBinding
import com.tikonsil.tikonsil509admin.databinding.FragmentCountryPriceBinding
import com.tikonsil.tikonsil509admin.domain.model.CountryPrice
import com.tikonsil.tikonsil509admin.presentation.countryprice.CountryPriceViewModel
import com.tikonsil.tikonsil509admin.ui.fragment.base.BaseFragment
import com.tikonsil.tikonsil509admin.utils.UtilsView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CountryPriceFragment : BaseFragment<FragmentCountryPriceBinding,CountryPriceViewModel> () {

    private val bottomSheetDialog by lazy {
        BottomSheetDialog(
            requireContext(),
            R.style.BottomSheetDialoTheme
        )
    }
    private val viewModel:CountryPriceViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val bottomNavigationView =
            activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.visibility = View.GONE
        showBottomSheet()
    }

    private fun validateRealtime(bottomViewBinding: BottomSheetUpdateCountryPriceBinding) {
        with(bottomViewBinding) {
            UtilsView.countryPriceValidator(layoutMonCash,monCash,layoutLaPouLa,lapoula,layoutNatCash,natcash,btnEditDialog,requireContext())
        }

    }


    private fun showBottomSheet() {
        val bottomViewBinding: BottomSheetUpdateCountryPriceBinding =
            BottomSheetUpdateCountryPriceBinding.inflate(LayoutInflater.from(context))
        bottomSheetDialog.setContentView(bottomViewBinding.root)
        bottomSheetDialog.setCancelable(false)
        bottomSheetDialog.show()
        viewModel.getCountryPriceResponse.observe(viewLifecycleOwner){
            it.forEach {prices->
                bottomViewBinding.dataBinding = prices
            }
         bottomViewBinding.btnEditDialog.setOnClickListener(updateData(it,bottomViewBinding))
        }
        validateRealtime(bottomViewBinding)

        viewModel.resultUpdate.observe(viewLifecycleOwner){task->
            if (task.isSuccessful){
                Toast.makeText(bottomSheetDialog.context, "Se actualizo correctamente los datos", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(bottomSheetDialog.context, "No se pudo actualizar ${task.result}", Toast.LENGTH_SHORT).show()

            }
        }

        bottomViewBinding.btnEditDialogCancel.setOnClickListener {
            navController.navigate(R.id.action_countryPriceFragment_to_homeFragment)
            bottomSheetDialog.dismiss()
        }
    }

    private fun updateData(
        price: MutableList<CountryPrice>?,
        bottomViewBinding: BottomSheetUpdateCountryPriceBinding
    ): View.OnClickListener {
      return View.OnClickListener {
        price?.map {countryPrice->
            viewModel.updatePrices(countryPrice.idKey!!,bottomViewBinding.monCash.text.toString(),
            bottomViewBinding.lapoula.text.toString(),bottomViewBinding.natcash.text.toString())
        }
      }
    }


    override fun onResume() {
        super.onResume()
        viewModel.getCountryPrice()
    }

    override fun getViewModel()=CountryPriceViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentCountryPriceBinding.inflate(inflater,container,false)


}