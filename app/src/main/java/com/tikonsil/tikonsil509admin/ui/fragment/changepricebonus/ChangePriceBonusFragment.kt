package com.tikonsil.tikonsil509admin.ui.fragment.changepricebonus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.tikonsil.tikonsi.ChangePriceBonusValidate
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.databinding.FragmentChangePriceBonusBinding
import com.tikonsil.tikonsil509admin.presentation.bonususer.BonusUserViewModel


class ChangePriceBonusFragment :ChangePriceBonusValidate<BonusUserViewModel,FragmentChangePriceBonusBinding>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list_bonus =resources.getStringArray(R.array.bonus_list)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.item_bonus_price,list_bonus)
        with(binding.autoCompleteTextView){
            setAdapter(arrayAdapter)
            onItemClickListener=this@ChangePriceBonusFragment
        }
        observedatabonus()
    }
    override fun getViewModel()=BonusUserViewModel::class.java
    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?)= FragmentChangePriceBonusBinding.inflate(inflater,container,false)

}