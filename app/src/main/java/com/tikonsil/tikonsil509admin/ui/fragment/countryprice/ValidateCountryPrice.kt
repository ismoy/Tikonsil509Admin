package com.tikonsil.tikonsil509admin.ui.fragment.countryprice

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.domain.repository.countryprice.CountryPriceRepository
import com.tikonsil.tikonsil509admin.presentation.countryprice.CountryPriceViewModel
import com.tikonsil.tikonsil509admin.presentation.countryprice.CountryPriceViewModelProvider

/** * Created by ISMOY BELIZAIRE on 15/05/2022. */
abstract class ValidateCountryPrice<VB : ViewBinding, VM : ViewModel> : Fragment() {
    protected lateinit var binding: VB
    protected lateinit var viewmodel: CountryPriceViewModel
    private lateinit var layouttopuphaiti: TextInputLayout
    private lateinit var topuphaiti: TextInputEditText
    private lateinit var layoutmoncashhaiti: TextInputLayout
    private lateinit var moncashhaiti: TextInputEditText
    private lateinit var layouttopupcuba: TextInputLayout
    private lateinit var topupcuba: TextInputEditText
    private lateinit var layouttopupmexico: TextInputLayout
    private lateinit var topupmexico: TextInputEditText
    private lateinit var layouttopuppanama: TextInputLayout
    private lateinit var topuppanama: TextInputEditText
    private lateinit var layouttopuprd: TextInputLayout
    private lateinit var topuprd: TextInputEditText
    private lateinit var layouttopupbrasil: TextInputLayout
    private lateinit var topupbrasil: TextInputEditText
    private lateinit var layouttopupchile: TextInputLayout
    private lateinit var topupchile: TextInputEditText
    private lateinit var layouttopupusa: TextInputLayout
    private lateinit var topupusa: TextInputEditText
    private lateinit var layoutlapoulahaiti: TextInputLayout
    private lateinit var lapoulahaiti: TextInputEditText
    private lateinit var layoutnatcashhaiti: TextInputLayout
    private lateinit var natcashhaiti: TextInputEditText
    protected lateinit var btn_update_price: Button
    protected lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getFragmentBinding(inflater, container)
        val repository = CountryPriceRepository()
        val factory = CountryPriceViewModelProvider(repository)
        viewmodel = ViewModelProvider(
            requireActivity(),
            factory
        )[CountryPriceViewModel::class.java]
        topupusa = binding.root.findViewById(R.id.topuppriceusa)
        topupchile = binding.root.findViewById(R.id.topuppricechile)
        topupbrasil = binding.root.findViewById(R.id.topuppricebrasil)
        topuprd = binding.root.findViewById(R.id.topuppricerd)
        topuppanama = binding.root.findViewById(R.id.topuppricepanama)
        topupmexico = binding.root.findViewById(R.id.topuppricemexico)
        topupcuba = binding.root.findViewById(R.id.topuppricecuba)
        moncashhaiti = binding.root.findViewById(R.id.moncashpricehaiti)
        topuphaiti = binding.root.findViewById(R.id.toptuppricehaiti)
        layouttopuphaiti = binding.root.findViewById(R.id.layouttopuppricehaiti)
        layoutmoncashhaiti = binding.root.findViewById(R.id.layoutmoncashpricehaiti)
        layouttopupcuba = binding.root.findViewById(R.id.layouttopuppricecuba)
        layouttopupmexico = binding.root.findViewById(R.id.layouttopuppricemexico)
        layouttopuppanama = binding.root.findViewById(R.id.layouttopuppricepanama)
        layouttopuprd = binding.root.findViewById(R.id.layouttopuppricerd)
        layouttopupbrasil = binding.root.findViewById(R.id.layouttopuppricebrasil)
        layouttopupchile = binding.root.findViewById(R.id.layouttopuppricechile)
        layouttopupusa = binding.root.findViewById(R.id.layouttopuppriceusa)
        layoutlapoulahaiti = binding.root.findViewById(R.id.layoutlapoulapricehaiti)
        lapoulahaiti = binding.root.findViewById(R.id.lapoulapricehaiti)
        layoutnatcashhaiti = binding.root.findViewById(R.id.layoutnatcashpricehaiti)
        natcashhaiti = binding.root.findViewById(R.id.natcashpricehaiti)
        btn_update_price = binding.root.findViewById(R.id.btn_update_price)

        return binding.root
    }

    fun updateData() {
        val map: MutableMap<String?, Any?> = HashMap()
        map.apply {
            put("priceRD", topuprd.text.toString().toDouble())
            put("pricebrasil", topupbrasil.text.toString().toDouble())
            put("pricechile", topupchile.text.toString().toDouble())
            put("pricecuba", topupcuba.text.toString().toDouble())
            put("pricemexico", topupmexico.text.toString().toDouble())
            put("pricemoncashhaiti", moncashhaiti.text.toString().toDouble())
            put("pricepanama", topuppanama.text.toString().toDouble())
            put("pricetopuphaiti", topuphaiti.text.toString().toDouble())
            put("priceus", topupusa.text.toString().toDouble())
            put("pricenatcashhaiti", natcashhaiti.text.toString().toDouble())
            put("pricelapoulahaiti", lapoulahaiti.text.toString().toDouble())
        }
        val mDatabase: DatabaseReference =
            FirebaseDatabase.getInstance().reference.child("CountryPrice")
        mDatabase.updateChildren(map)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        "Actualizado correctamente",
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate(R.id.action_countryPriceFragment_to_homeFragment)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "error al cambiar los datos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun observeDataCountryPrice() {
        viewmodel.getCountryPrice().observe(viewLifecycleOwner, Observer {
            it.forEach { data ->
                topupusa.setText(data.priceus.toString())
                topupchile.setText(data.pricechile.toString())
                topupbrasil.setText(data.pricebrasil.toString())
                topuprd.setText(data.priceRD.toString())
                topuppanama.setText(data.pricepanama.toString())
                topupmexico.setText(data.pricemexico.toString())
                topupcuba.setText(data.pricecuba.toString())
                topuphaiti.setText(data.pricetopuphaiti.toString())
                moncashhaiti.setText(data.pricemoncashhaiti.toString())
                lapoulahaiti.setText(data.pricelapoulahaiti.toString())
                natcashhaiti.setText(data.pricenatcashhaiti.toString())
            }
        })
    }

    fun validateRealtime() {
        topupusa.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                when {
                    topupusa.text.toString().isEmpty() -> {
                        layouttopupusa.helperText = getString(R.string.erroremptyfield)
                        btn_update_price.isEnabled = false
                    }
                    else -> {
                        layouttopupusa.helperText = ""
                        btn_update_price.isEnabled = true
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        topupchile.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                when {
                    topupchile.text.toString().isEmpty() -> {
                        layouttopupchile.helperText = getString(R.string.erroremptyfield)
                        btn_update_price.isEnabled = false
                    }
                    else -> {
                        layouttopupchile.helperText = ""
                        btn_update_price.isEnabled = true
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        topupbrasil.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                when {
                    topupbrasil.text.toString().isEmpty() -> {
                        layouttopupbrasil.helperText = getString(R.string.erroremptyfield)
                        btn_update_price.isEnabled = false
                    }
                    else -> {
                        layouttopupbrasil.helperText = ""
                        btn_update_price.isEnabled = true
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        topuprd.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                when {
                    topuprd.text.toString().isEmpty() -> {
                        layouttopuprd.helperText = getString(R.string.erroremptyfield)
                        btn_update_price.isEnabled = false
                    }
                    else -> {
                        layouttopuprd.helperText = ""
                        btn_update_price.isEnabled = true
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        topuppanama.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                when {
                    topuppanama.text.toString().isEmpty() -> {
                        layouttopuppanama.helperText = getString(R.string.erroremptyfield)
                        btn_update_price.isEnabled = false
                    }
                    else -> {
                        layouttopuppanama.helperText = ""
                        btn_update_price.isEnabled = true
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        topupmexico.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                when {
                    topupmexico.text.toString().isEmpty() -> {
                        layouttopupmexico.helperText = getString(R.string.erroremptyfield)
                        btn_update_price.isEnabled = false
                    }
                    else -> {
                        layouttopupmexico.helperText = ""
                        btn_update_price.isEnabled = true
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        topupcuba.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                when {
                    topupcuba.text.toString().isEmpty() -> {
                        layouttopupcuba.helperText = getString(R.string.erroremptyfield)
                        btn_update_price.isEnabled = false
                    }
                    else -> {
                        layouttopupcuba.helperText = ""
                        btn_update_price.isEnabled = true
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        moncashhaiti.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                when {
                    moncashhaiti.text.toString().isEmpty() -> {
                        layoutmoncashhaiti.helperText = getString(R.string.erroremptyfield)
                        btn_update_price.isEnabled = false
                    }
                    else -> {
                        layoutmoncashhaiti.helperText = ""
                        btn_update_price.isEnabled = true
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        topuphaiti.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                when {
                    topuphaiti.text.toString().isEmpty() -> {
                        layouttopuphaiti.helperText = getString(R.string.erroremptyfield)
                        btn_update_price.isEnabled = false
                    }
                    else -> {
                        layouttopuphaiti.helperText = ""
                        btn_update_price.isEnabled = true
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        lapoulahaiti.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                when {
                    lapoulahaiti.text.toString().isEmpty() -> {
                        layoutlapoulahaiti.helperText = getString(R.string.erroremptyfield)
                        btn_update_price.isEnabled = false
                    }
                    else -> {
                        layoutlapoulahaiti.helperText = ""
                        btn_update_price.isEnabled = true
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        natcashhaiti.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                when {
                    natcashhaiti.text.toString().isEmpty() -> {
                        layoutnatcashhaiti.helperText = getString(R.string.erroremptyfield)
                        btn_update_price.isEnabled = false
                    }
                    else -> {
                        layoutnatcashhaiti.helperText = ""
                        btn_update_price.isEnabled = true
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })


    }

    abstract fun getViewModel(): Class<VM>
    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): VB
}