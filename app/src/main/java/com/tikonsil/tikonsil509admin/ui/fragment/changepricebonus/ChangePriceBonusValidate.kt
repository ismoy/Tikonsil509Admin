package com.tikonsil.tikonsi

import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.data.remote.provider.UpdateBonusProvider
import com.tikonsil.tikonsil509admin.domain.repository.bonususer.BonusUserRepository
import com.tikonsil.tikonsil509admin.presentation.bonususer.BonusUserViewModel
import com.tikonsil.tikonsil509admin.presentation.bonususer.BonusUserViewModelProvider

/** * Created by ISMOY BELIZAIRE on 29/05/2022. */
abstract class ChangePriceBonusValidate<VM:ViewModel,VB:ViewBinding> :Fragment(), AdapterView.OnItemClickListener {
    protected lateinit var binding:VB
    private lateinit var viewmodel: BonusUserViewModel
    private lateinit var updateBonusProvider: UpdateBonusProvider
    private var btnUpdate: Button?=null
    private var autoCompleteTextView:AutoCompleteTextView?=null
    private var edtbonus:TextInputEditText?=null
    private var layoutedtbonus:TextInputLayout?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getFragmentBinding(inflater, container)
        val repository = BonusUserRepository()
        val factory = BonusUserViewModelProvider(repository)
        viewmodel = ViewModelProvider(requireActivity(),factory)[BonusUserViewModel::class.java]
         autoCompleteTextView =binding.root.findViewById(R.id.autoCompleteTextView)
        updateBonusProvider = UpdateBonusProvider()
        btnUpdate =binding.root.findViewById(R.id.updatebonus)
        edtbonus =binding.root.findViewById(R.id.edtbonus)
        layoutedtbonus=binding.root.findViewById(R.id.layoutedtbonus)
        return binding.root
    }
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val item = parent?.getItemAtPosition((position)).toString()
        Log.d("SELECTEDNAME",item)
        when (item) {
            "TOPUP HAITI" -> {
                layoutedtbonus?.isVisible =true
                btnUpdate?.isVisible =true
                edtbonus?.setText( TOPUPHAITI.toString())
                btnUpdate?.setOnClickListener {
                    updateBonusTopUpHaiti()
                }

            }
            "MONCASH HAITI" -> {
                layoutedtbonus?.isVisible =true
                btnUpdate?.isVisible =true
                edtbonus?.setText( MONCASHHAITI.toString())
                btnUpdate?.setOnClickListener {
                    updateBonusMoncashHaiti()
                }
            }
            "NATCASH HAITI"->{
                layoutedtbonus?.isVisible=true
                btnUpdate?.isVisible=true
                edtbonus?.setText( NATCASHHAITI.toString())
                btnUpdate?.setOnClickListener {
                    updateBonusNatcashHaiti()
                }
            }
            "LAPOULA HAITI"->{
                layoutedtbonus?.isVisible=true
                btnUpdate?.isVisible=true
                edtbonus?.setText( LAPOULAHAITI.toString())
                btnUpdate?.setOnClickListener {
                    updateBonusLapoulaHaiti()
                }
            }
            "TOPUP CHILE"->{
                layoutedtbonus?.isVisible=true
                btnUpdate?.isVisible=true
                edtbonus?.setText( TOPUPCHILE.toString())
                btnUpdate?.setOnClickListener {
                    updateBonusTopUpChile()
                }
            }
            "MONCASH CHILE"->{
                layoutedtbonus?.isVisible=true
                btnUpdate?.isVisible=true
                edtbonus?.setText( MONCASHCHILE.toString())
                btnUpdate?.setOnClickListener {
                    updateBonusMoncashChile()
                }
            }
            "NATCASH CHILE"->{
                layoutedtbonus?.isVisible=true
                btnUpdate?.isVisible=true
                edtbonus?.setText( NATCASHCHILE.toString())
                btnUpdate?.setOnClickListener {
                    updateBonusNatcashChile()
                }
            }
            "LAPOULA CHILE"->{
                layoutedtbonus?.isVisible=true
                btnUpdate?.isVisible=true
                edtbonus?.setText( LAPOULACHILE.toString())
                btnUpdate?.setOnClickListener {
                    updateBonusLapoulaChile()
                }
            }
            "TOPUP CUBA"->{
                layoutedtbonus?.isVisible=true
                btnUpdate?.isVisible=true
                edtbonus?.setText( TOPUPCUBA.toString())
                btnUpdate?.setOnClickListener {
                    updateBonusTopUpCuba()
                }
            }
            "MONCASH CUBA"->{
                layoutedtbonus?.isVisible=true
                btnUpdate?.isVisible=true
                edtbonus?.setText( MONCASHCUBA.toString())
                btnUpdate?.setOnClickListener {
                    updateBonusMoncashCuba()
                }
            }
            "NATCASH CUBA"->{
                layoutedtbonus?.isVisible=true
                btnUpdate?.isVisible=true
                edtbonus?.setText( NATCASHCUBA.toString())
                btnUpdate?.setOnClickListener {
                    updateBonusNatcashCuba()
                }
            }
            "LAPOULA CUBA"->{
                layoutedtbonus?.isVisible=true
                btnUpdate?.isVisible=true
                edtbonus?.setText( LAPOULACUBA.toString())
                btnUpdate?.setOnClickListener {
                    updateBonusLapoulaCuba()
                }
            }
            "TOPUP REPUBLICAN DOMINIK"->{
                layoutedtbonus?.isVisible=true
                btnUpdate?.isVisible=true
                edtbonus?.setText( TOPUPREPUBLICANDOMINIK.toString())
                btnUpdate?.setOnClickListener {
                    updateBonusTopUpRd()
                }
            }
            "MONCASH REPUBLICAN DOMINIK"->{
                layoutedtbonus?.isVisible=true
                btnUpdate?.isVisible=true
                edtbonus?.setText( MONCASHREPUBLICANDOMINIK.toString())
                btnUpdate?.setOnClickListener {
                    updateBonusMoncashRd()
                }
            }
            "NATCASH REPUBLICAN DOMINIK"->{
                layoutedtbonus?.isVisible=true
                btnUpdate?.isVisible=true
                edtbonus?.setText( NATCASHREPUBLICANDOMINIK.toString())
                btnUpdate?.setOnClickListener {
                    updateBonusNatcashRd()
                }
            }
            "LAPOULA REPUBLICAN DOMINIK"->{
                layoutedtbonus?.isVisible=true
                btnUpdate?.isVisible=true
                edtbonus?.setText( LAPOULAREPUBLICANDOMINIK.toString())
                btnUpdate?.setOnClickListener {
                    updateBonusLapoulaRd()
                }
            }
            "TOPUP PANAMA"->{
                layoutedtbonus?.isVisible=true
                btnUpdate?.isVisible=true
                edtbonus?.setText( TOPUPPANAMA.toString())
                btnUpdate?.setOnClickListener {
                    updateBonusTopUpPanama()
                }
            }
            "MONCASH PANAMA"->{
                layoutedtbonus?.isVisible=true
                btnUpdate?.isVisible=true
                edtbonus?.setText( MONCASHPANAMA.toString())
                btnUpdate?.setOnClickListener {
                    updateBonusMoncashPanama()
                }
            }
            "NATCASH PANAMA"->{
                layoutedtbonus?.isVisible=true
                btnUpdate?.isVisible=true
                edtbonus?.setText( NATCASHPANAMA.toString())
                btnUpdate?.setOnClickListener {
                    updateBonusNatcashPanama()
                }
            }
            "LAPOULA PANAMA"->{
                layoutedtbonus?.isVisible=true
                btnUpdate?.isVisible=true
                edtbonus?.setText( LAPOULAPANAMA.toString())
                btnUpdate?.setOnClickListener {
                    updateBonusLapoulaPanama()
                }
            }
            "TOPUP MEXICO"->{
                layoutedtbonus?.isVisible=true
                btnUpdate?.isVisible=true
                edtbonus?.setText( TOPUPMEXICO.toString())
                btnUpdate?.setOnClickListener {
                    updateBonusTopUpMexico()
                }
            }
            "MONCASH MEXICO"->{
                layoutedtbonus?.isVisible=true
                btnUpdate?.isVisible=true
                edtbonus?.setText(MONCASHMEXICO.toString())
                btnUpdate?.setOnClickListener {
                    updateBonusMoncashMexico()
                }
            }
            "NATCASH MEXICO"->{
                layoutedtbonus?.isVisible=true
                btnUpdate?.isVisible=true
                edtbonus?.setText(NATCASHMEXICO.toString())
                btnUpdate?.setOnClickListener {
                    updateBonusNatcashMexico()
                }

            }
            "LAPOULA MEXICO"->{
                layoutedtbonus?.isVisible=true
                btnUpdate?.isVisible=true
                edtbonus?.setText(LAPOULAMEXICO.toString())
                btnUpdate?.setOnClickListener {
                    updateBonusLapoulaMexico()
                }
            }
            "TOPUP BRASIL"->{
                layoutedtbonus?.isVisible=true
                btnUpdate?.isVisible=true
                edtbonus?.setText(TOPUPBRAZIL.toString())
                btnUpdate?.setOnClickListener {
                    updateBonusTopUpBrazil()
                }
            }
            "MONCASH BRASIL"->{
                layoutedtbonus?.isVisible=true
                btnUpdate?.isVisible=true
                edtbonus?.setText(MONCASHBRAZIL.toString())
                btnUpdate?.setOnClickListener {
                    updateBonusMoncashBrazil()
                }
            }
            "NATCASH BRASIL"->{
                layoutedtbonus?.isVisible=true
                btnUpdate?.isVisible=true
                edtbonus?.setText(NATCASHBRAZIL.toString())
                btnUpdate?.setOnClickListener {
                    updateBonusNatcashBrazil()
                }
            }
            "LAPOULA BRASIL"->{
                layoutedtbonus?.isVisible=true
                btnUpdate?.isVisible=true
                edtbonus?.setText(LAPOULABRAZIL.toString())
                btnUpdate?.setOnClickListener {
                    updateBonusLapoulaBrazil()
                }
            }
            "TOPUP OTHER COUNTRY"->{
                layoutedtbonus?.isVisible=true
                btnUpdate?.isVisible=true
                edtbonus?.setText(TOPUPOTHERCOUNTRY.toString())
                btnUpdate?.setOnClickListener {
                    updateBonusTopUpOtherCountry()
                }
            }
            "MONCASH OTHER COUNTRY"->{
                layoutedtbonus?.isVisible=true
                btnUpdate?.isVisible=true
                edtbonus?.setText(MONCASHOTHERCOUNTRY.toString())
                btnUpdate?.setOnClickListener {
                    updateBonusMoncashOtherCountry()
                }
            }
            "NATCASH OTHER COUNTRY"->{
                layoutedtbonus?.isVisible=true
                btnUpdate?.isVisible=true
                edtbonus?.setText(NATCASHOTHERCOUNTRY.toString())
                btnUpdate?.setOnClickListener {
                    updateBonusNatcashOtherCountry()
                }
            }
            "LAPOULA OTHER COUNTRY"->{
                layoutedtbonus?.isVisible=true
                btnUpdate?.isVisible=true
                edtbonus?.setText(LAPOULAOTHERCOUNTRY.toString())
                btnUpdate?.setOnClickListener {
                    updateBonusLapoulaOtherCountry()
                }
            }
            else -> {
                layoutedtbonus?.isVisible =false
                btnUpdate?.isVisible =false
            }
        }
    }

    private fun updateBonusNatcashHaiti() {
        when{
            edtbonus?.text.toString().isEmpty()->{
                layoutedtbonus?.helperText=getString(R.string.erroremptyfield)
            }else->{
                layoutedtbonus?.helperText=""
                updateBonusProvider.updateBonusNatCashHaiti(edtbonus?.text.toString().toFloat())
            Toast.makeText(requireContext(), "Datos actualizado correctamente", Toast.LENGTH_SHORT).show()
        }
        }
    }
    private fun updateBonusMoncashHaiti() {
        when {
            edtbonus?.text.toString().isEmpty() -> {
                layoutedtbonus?.helperText=getString(R.string.erroremptyfield)
            }
            else -> {
                layoutedtbonus?.helperText=""
                updateBonusProvider.updateBonusMoncashHaiti(edtbonus?.text.toString().toFloat())
                Toast.makeText(requireContext(), "Datos actualizado correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun updateBonusTopUpHaiti() {
        when {
            edtbonus?.text.toString().isEmpty() -> {
                layoutedtbonus?.helperText=getString(R.string.erroremptyfield)
            }
            else -> {
                layoutedtbonus?.helperText=""
                updateBonusProvider.updateBonusTopUpHaiti(edtbonus?.text.toString().toFloat())
                Toast.makeText(requireContext(), "Datos actualizado correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun updateBonusLapoulaHaiti() {
        when{
            edtbonus?.text.toString().isEmpty()->{
                layoutedtbonus?.helperText=getString(R.string.erroremptyfield)
            }else->{
            layoutedtbonus?.helperText=""
            updateBonusProvider.updateBonusLapoulaHaiti(edtbonus?.text.toString().toFloat())
            Toast.makeText(requireContext(), "Datos actualizado correctamente", Toast.LENGTH_SHORT).show()
        }
        }
    }
    private fun updateBonusNatcashChile() {
        when{
            edtbonus?.text.toString().isEmpty()->{
                layoutedtbonus?.helperText=getString(R.string.erroremptyfield)
            }else->{
            layoutedtbonus?.helperText=""
            updateBonusProvider.updateBonusNatCashChile(edtbonus?.text.toString().toFloat())
            Toast.makeText(requireContext(), "Datos actualizado correctamente", Toast.LENGTH_SHORT).show()
        }
        }
    }
    private fun updateBonusMoncashChile() {
        when {
            edtbonus?.text.toString().isEmpty() -> {
                layoutedtbonus?.helperText=getString(R.string.erroremptyfield)
            }
            else -> {
                layoutedtbonus?.helperText=""
                updateBonusProvider.updateBonusMoncashChile(edtbonus?.text.toString().toFloat())
                Toast.makeText(requireContext(), "Datos actualizado correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun updateBonusTopUpChile() {
        when {
            edtbonus?.text.toString().isEmpty() -> {
                layoutedtbonus?.helperText=getString(R.string.erroremptyfield)
            }
            else -> {
                layoutedtbonus?.helperText=""
                updateBonusProvider.updateBonusTopUpChile(edtbonus?.text.toString().toFloat())
                Toast.makeText(requireContext(), "Datos actualizado correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun updateBonusLapoulaChile() {
        when{
            edtbonus?.text.toString().isEmpty()->{
                layoutedtbonus?.helperText=getString(R.string.erroremptyfield)
            }else->{
            layoutedtbonus?.helperText=""
            updateBonusProvider.updateBonusLapoulaChile(edtbonus?.text.toString().toFloat())
            Toast.makeText(requireContext(), "Datos actualizado correctamente", Toast.LENGTH_SHORT).show()
        }
        }
    }
    private fun updateBonusNatcashCuba() {
        when{
            edtbonus?.text.toString().isEmpty()->{
                layoutedtbonus?.helperText=getString(R.string.erroremptyfield)
            }else->{
            layoutedtbonus?.helperText=""
            updateBonusProvider.updateBonusNatCashCuba(edtbonus?.text.toString().toFloat())
            Toast.makeText(requireContext(), "Datos actualizado correctamente", Toast.LENGTH_SHORT).show()
        }
        }
    }
    private fun updateBonusMoncashCuba() {
        when {
            edtbonus?.text.toString().isEmpty() -> {
                layoutedtbonus?.helperText=getString(R.string.erroremptyfield)
            }
            else -> {
                layoutedtbonus?.helperText=""
                updateBonusProvider.updateBonusMoncashCuba(edtbonus?.text.toString().toFloat())
                Toast.makeText(requireContext(), "Datos actualizado correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun updateBonusTopUpCuba() {
        when {
            edtbonus?.text.toString().isEmpty() -> {
                layoutedtbonus?.helperText=getString(R.string.erroremptyfield)
            }
            else -> {
                layoutedtbonus?.helperText=""
                updateBonusProvider.updateBonusTopUpCuba(edtbonus?.text.toString().toFloat())
                Toast.makeText(requireContext(), "Datos actualizado correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun updateBonusLapoulaCuba() {
        when{
            edtbonus?.text.toString().isEmpty()->{
                layoutedtbonus?.helperText=getString(R.string.erroremptyfield)
            }else->{
            layoutedtbonus?.helperText=""
            updateBonusProvider.updateBonusLapoulaCuba(edtbonus?.text.toString().toFloat())
            Toast.makeText(requireContext(), "Datos actualizado correctamente", Toast.LENGTH_SHORT).show()
        }
        }
    }
    private fun updateBonusNatcashPanama() {
        when{
            edtbonus?.text.toString().isEmpty()->{
                layoutedtbonus?.helperText=getString(R.string.erroremptyfield)
            }else->{
            layoutedtbonus?.helperText=""
            updateBonusProvider.updateBonusNatCashPanama(edtbonus?.text.toString().toFloat())
            Toast.makeText(requireContext(), "Datos actualizado correctamente", Toast.LENGTH_SHORT).show()
        }
        }
    }
    private fun updateBonusMoncashPanama() {
        when {
            edtbonus?.text.toString().isEmpty() -> {
                layoutedtbonus?.helperText=getString(R.string.erroremptyfield)
            }
            else -> {
                layoutedtbonus?.helperText=""
                updateBonusProvider.updateBonusMoncashPanama(edtbonus?.text.toString().toFloat())
                Toast.makeText(requireContext(), "Datos actualizado correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun updateBonusTopUpPanama() {
        when {
            edtbonus?.text.toString().isEmpty() -> {
                layoutedtbonus?.helperText=getString(R.string.erroremptyfield)
            }
            else -> {
                layoutedtbonus?.helperText=""
                updateBonusProvider.updateBonusTopUpPanama(edtbonus?.text.toString().toFloat())
                Toast.makeText(requireContext(), "Datos actualizado correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun updateBonusLapoulaPanama() {
        when{
            edtbonus?.text.toString().isEmpty()->{
                layoutedtbonus?.helperText=getString(R.string.erroremptyfield)
            }else->{
            layoutedtbonus?.helperText=""
            updateBonusProvider.updateBonusLapoulaPanama(edtbonus?.text.toString().toFloat())
            Toast.makeText(requireContext(), "Datos actualizado correctamente", Toast.LENGTH_SHORT).show()
        }
        }
    }
    private fun updateBonusNatcashBrazil() {
        when{
            edtbonus?.text.toString().isEmpty()->{
                layoutedtbonus?.helperText=getString(R.string.erroremptyfield)
            }else->{
            layoutedtbonus?.helperText=""
            updateBonusProvider.updateBonusNatCashBrazil(edtbonus?.text.toString().toFloat())
            Toast.makeText(requireContext(), "Datos actualizado correctamente", Toast.LENGTH_SHORT).show()
        }
        }
    }
    private fun updateBonusMoncashBrazil() {
        when {
            edtbonus?.text.toString().isEmpty() -> {
                layoutedtbonus?.helperText=getString(R.string.erroremptyfield)
            }
            else -> {
                layoutedtbonus?.helperText=""
                updateBonusProvider.updateBonusMoncashBrazil(edtbonus?.text.toString().toFloat())
                Toast.makeText(requireContext(), "Datos actualizado correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun updateBonusTopUpBrazil() {
        when {
            edtbonus?.text.toString().isEmpty() -> {
                layoutedtbonus?.helperText=getString(R.string.erroremptyfield)
            }
            else -> {
                layoutedtbonus?.helperText=""
                updateBonusProvider.updateBonusTopUpBrazil(edtbonus?.text.toString().toFloat())
                Toast.makeText(requireContext(), "Datos actualizado correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun updateBonusLapoulaBrazil() {
        when{
            edtbonus?.text.toString().isEmpty()->{
                layoutedtbonus?.helperText=getString(R.string.erroremptyfield)
            }else->{
            layoutedtbonus?.helperText=""
            updateBonusProvider.updateBonusLapoulaBrazil(edtbonus?.text.toString().toFloat())
            Toast.makeText(requireContext(), "Datos actualizado correctamente", Toast.LENGTH_SHORT).show()
        }
        }
    }
    private fun updateBonusNatcashMexico() {
        when{
            edtbonus?.text.toString().isEmpty()->{
                layoutedtbonus?.helperText=getString(R.string.erroremptyfield)
            }else->{
            layoutedtbonus?.helperText=""
            updateBonusProvider.updateBonusNatCashMexico(edtbonus?.text.toString().toFloat())
            Toast.makeText(requireContext(), "Datos actualizado correctamente", Toast.LENGTH_SHORT).show()
        }
        }
    }
    private fun updateBonusMoncashMexico() {
        when {
            edtbonus?.text.toString().isEmpty() -> {
                layoutedtbonus?.helperText=getString(R.string.erroremptyfield)
            }
            else -> {
                layoutedtbonus?.helperText=""
                updateBonusProvider.updateBonusMoncashMexico(edtbonus?.text.toString().toFloat())
                Toast.makeText(requireContext(), "Datos actualizado correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun updateBonusTopUpMexico() {
        when {
            edtbonus?.text.toString().isEmpty() -> {
                layoutedtbonus?.helperText=getString(R.string.erroremptyfield)
            }
            else -> {
                layoutedtbonus?.helperText=""
                updateBonusProvider.updateBonusTopUpMexico(edtbonus?.text.toString().toFloat())
                Toast.makeText(requireContext(), "Datos actualizado correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun updateBonusLapoulaMexico() {
        when{
            edtbonus?.text.toString().isEmpty()->{
                layoutedtbonus?.helperText=getString(R.string.erroremptyfield)
            }else->{
            layoutedtbonus?.helperText=""
            updateBonusProvider.updateBonusLapoulaMexico(edtbonus?.text.toString().toFloat())
            Toast.makeText(requireContext(), "Datos actualizado correctamente", Toast.LENGTH_SHORT).show()
        }
        }
    }
    private fun updateBonusNatcashRd() {
        when{
            edtbonus?.text.toString().isEmpty()->{
                layoutedtbonus?.helperText=getString(R.string.erroremptyfield)
            }else->{
            layoutedtbonus?.helperText=""
            updateBonusProvider.updateBonusNatCashRd(edtbonus?.text.toString().toFloat())
            Toast.makeText(requireContext(), "Datos actualizado correctamente", Toast.LENGTH_SHORT).show()
        }
        }
    }
    private fun updateBonusMoncashRd() {
        when {
            edtbonus?.text.toString().isEmpty() -> {
                layoutedtbonus?.helperText=getString(R.string.erroremptyfield)
            }
            else -> {
                layoutedtbonus?.helperText=""
                updateBonusProvider.updateBonusMoncashRd(edtbonus?.text.toString().toFloat())
                Toast.makeText(requireContext(), "Datos actualizado correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun updateBonusTopUpRd() {
        when {
            edtbonus?.text.toString().isEmpty() -> {
                layoutedtbonus?.helperText=getString(R.string.erroremptyfield)
            }
            else -> {
                layoutedtbonus?.helperText=""
                updateBonusProvider.updateBonusTopUpRd(edtbonus?.text.toString().toFloat())
                Toast.makeText(requireContext(), "Datos actualizado correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun updateBonusLapoulaRd() {
        when{
            edtbonus?.text.toString().isEmpty()->{
                layoutedtbonus?.helperText=getString(R.string.erroremptyfield)
            }else->{
            layoutedtbonus?.helperText=""
            updateBonusProvider.updateBonusLapoulaRd(edtbonus?.text.toString().toFloat())
            Toast.makeText(requireContext(), "Datos actualizado correctamente", Toast.LENGTH_SHORT).show()
        }
        }
    }
    private fun updateBonusNatcashOtherCountry() {
        when{
            edtbonus?.text.toString().isEmpty()->{
                layoutedtbonus?.helperText=getString(R.string.erroremptyfield)
            }else->{
            layoutedtbonus?.helperText=""
            updateBonusProvider.updateBonusNatCashOtherCountry(edtbonus?.text.toString().toFloat())
            Toast.makeText(requireContext(), "Datos actualizado correctamente", Toast.LENGTH_SHORT).show()
        }
        }
    }
    private fun updateBonusMoncashOtherCountry() {
        when {
            edtbonus?.text.toString().isEmpty() -> {
                layoutedtbonus?.helperText=getString(R.string.erroremptyfield)
            }
            else -> {
                layoutedtbonus?.helperText=""
                updateBonusProvider.updateBonusMoncashOtherCountry(edtbonus?.text.toString().toFloat())
                Toast.makeText(requireContext(), "Datos actualizado correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun updateBonusTopUpOtherCountry() {
        when {
            edtbonus?.text.toString().isEmpty() -> {
                layoutedtbonus?.helperText=getString(R.string.erroremptyfield)
            }
            else -> {
                layoutedtbonus?.helperText=""
                updateBonusProvider.updateBonusTopUpOtherCountry(edtbonus?.text.toString().toFloat())
                Toast.makeText(requireContext(), "Datos actualizado correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun updateBonusLapoulaOtherCountry() {
        when{
            edtbonus?.text.toString().isEmpty()->{
                layoutedtbonus?.helperText=getString(R.string.erroremptyfield)
            }else->{
            layoutedtbonus?.helperText=""
            updateBonusProvider.updateBonusLapoulaOtherCountry(edtbonus?.text.toString().toFloat())
            Toast.makeText(requireContext(), "Datos actualizado correctamente", Toast.LENGTH_SHORT).show()
        }
        }
    }

    fun observedatabonus(){
        viewmodel.getBonusUser()
        viewmodel.myResponseGetUserBonus.observe(viewLifecycleOwner, Observer { bonus->
            if (bonus.isSuccessful){
                TOPUPHAITI=bonus.body()?.bounustopuphaiti
                MONCASHHAITI=bonus.body()?.bounusmoncashhaiti
                NATCASHHAITI=bonus.body()?.bounusnatcashhaiti
                LAPOULAHAITI=bonus.body()?.bounuslapoulahaiti
                TOPUPCHILE=bonus.body()?.bounustopupchile
                MONCASHCHILE=bonus.body()?.bounusmoncashchile
                NATCASHCHILE=bonus.body()?.bounusnatcashchile
                LAPOULACHILE=bonus.body()?.bounuslapoulachile
                TOPUPCUBA=bonus.body()?.bounustopupcuba
                MONCASHCUBA=bonus.body()?.bounusmoncashcuba
                LAPOULACUBA=bonus.body()?.bounuslapoulacuba
                NATCASHCUBA=bonus.body()?.bounusnatcashcuba
                TOPUPPANAMA=bonus.body()?.bounustopuppanama
                MONCASHPANAMA=bonus.body()?.bounusmoncashpanama
                NATCASHPANAMA=bonus.body()?.bounusnatcashpanama
                LAPOULAPANAMA=bonus.body()?.bounuslapoulapanama
                TOPUPBRAZIL=bonus.body()?.bounustopupbrazil
                MONCASHBRAZIL=bonus.body()?.bounusmoncashbrazil
                NATCASHBRAZIL=bonus.body()?.bounusnatcashbrazil
                LAPOULABRAZIL=bonus.body()?.bounuslapoulabrazil
                TOPUPMEXICO=bonus.body()?.bounustopupmexico
                MONCASHMEXICO=bonus.body()?.bounusmoncashmexico
                NATCASHMEXICO=bonus.body()?.bounusnatcashmexico
                LAPOULAMEXICO=bonus.body()?.bounuslapoulamexico
                TOPUPREPUBLICANDOMINIK=bonus.body()?.bounustopuprd
                MONCASHREPUBLICANDOMINIK=bonus.body()?.bounusmoncashrd
                NATCASHREPUBLICANDOMINIK=bonus.body()?.bounusnatcashrd
                LAPOULAREPUBLICANDOMINIK=bonus.body()?.bounuslapoulard
                TOPUPOTHERCOUNTRY=bonus.body()?.bounustopupothercountry
                MONCASHOTHERCOUNTRY=bonus.body()?.bounusmoncashothercountry
                NATCASHOTHERCOUNTRY=bonus.body()?.bounusnatcashothercountry
                LAPOULAOTHERCOUNTRY=bonus.body()?.bounuslapoulaothercountry
            }else{
                Toast.makeText(requireContext(), "problema", Toast.LENGTH_SHORT).show()
            }
        })
    }
    companion object{
        var TOPUPHAITI:Float?=null
        var MONCASHHAITI:Float?=null
        var NATCASHHAITI:Float?=null
        var LAPOULAHAITI:Float?=null
        var TOPUPCHILE:Float?=null
        var MONCASHCHILE:Float?=null
        var NATCASHCHILE:Float?=null
        var LAPOULACHILE:Float?=null
        var TOPUPCUBA:Float?=null
        var MONCASHCUBA:Float?=null
        var NATCASHCUBA:Float?=null
        var LAPOULACUBA:Float?=null
        var TOPUPPANAMA:Float?=null
        var MONCASHPANAMA:Float?=null
        var NATCASHPANAMA:Float?=null
        var LAPOULAPANAMA:Float?=null
        var TOPUPBRAZIL:Float?=null
        var MONCASHBRAZIL:Float?=null
        var NATCASHBRAZIL:Float?=null
        var LAPOULABRAZIL:Float?=null
        var TOPUPMEXICO:Float?=null
        var MONCASHMEXICO:Float?=null
        var NATCASHMEXICO:Float?=null
        var LAPOULAMEXICO:Float?=null
        var TOPUPREPUBLICANDOMINIK:Float?=null
        var MONCASHREPUBLICANDOMINIK:Float?=null
        var NATCASHREPUBLICANDOMINIK:Float?=null
        var LAPOULAREPUBLICANDOMINIK:Float?=null
        var TOPUPOTHERCOUNTRY:Float?=null
        var MONCASHOTHERCOUNTRY:Float?=null
        var NATCASHOTHERCOUNTRY:Float?=null
        var LAPOULAOTHERCOUNTRY:Float?=null
    }
    abstract fun getViewModel(): Class<VM>
    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): VB
}