package com.tikonsil.tikonsil509admin.ui.fragment.listIdProductInnoverit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.data.adapter.CostInnoveritAdapter
import com.tikonsil.tikonsil509admin.data.mapper.toDomain
import com.tikonsil.tikonsil509admin.data.mapper.toSalesErrorDomain
import com.tikonsil.tikonsil509admin.databinding.FragmentListIdProductInnoveritBinding
import com.tikonsil.tikonsil509admin.presentation.costInnoverit.PriceCostInnoveritViewModel
import com.tikonsil.tikonsil509admin.ui.fragment.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListIdProductInnoveritFragment :BaseFragment<FragmentListIdProductInnoveritBinding,PriceCostInnoveritViewModel>(),
    SearchView.OnQueryTextListener  {
     private val constInnoveritAdapter by lazy { CostInnoveritAdapter(requireContext()) }
     private val priceCostViewModel:PriceCostInnoveritViewModel by viewModels()

    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)
        val bottomNavigationView =
            activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.visibility = View.GONE

        recyclerView = binding.recyclerIdProduct
        searView = binding.searchViewProduct
        cardSearchView = binding.cardSearchViewIdProduct
        searView.setOnQueryTextListener(this)
        viewModelObserver()
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListIdProductInnoveritBinding = FragmentListIdProductInnoveritBinding.inflate(inflater,container,false)

    override fun getViewModel() =PriceCostInnoveritViewModel::class.java
    private fun viewModelObserver() {
        priceCostViewModel.listIdProduct.observe(viewLifecycleOwner){
            val costEntityItemList = it.map { entity -> entity.toDomain() }
           priceCostViewModel.insertCost(costEntityItemList)
        }
       priceCostViewModel.readAllData.observe(viewLifecycleOwner){
           setupRecyclerview()
            constInnoveritAdapter.submitList(it)
        }
    }

    private fun setupRecyclerview() {
        with(binding) {
            recyclerIdProduct.adapter = constInnoveritAdapter
            recyclerIdProduct.layoutManager = linearLayoutManager
            recyclerIdProduct.hasFixedSize()
        }

    }

    override fun onResume() {
        super.onResume()
        priceCostViewModel.getListProduct()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query!=null){
            searchDatabase(query)
        }
        return true
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"
        priceCostViewModel.searchDataBase(searchQuery).observe(viewLifecycleOwner){list->
            list.let {
                constInnoveritAdapter.submitList(it
                )
            }
        }
    }
}