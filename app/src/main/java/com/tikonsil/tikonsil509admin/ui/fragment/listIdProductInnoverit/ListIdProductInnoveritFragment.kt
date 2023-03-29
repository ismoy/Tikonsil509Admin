package com.tikonsil.tikonsil509admin.ui.fragment.listIdProductInnoverit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.data.adapter.CostInnoveritAdapter
import com.tikonsil.tikonsil509admin.databinding.FragmentListIdProductInnoveritBinding
import com.tikonsil.tikonsil509admin.presentation.costInnoverit.PriceCostInnoveritViewModel


class ListIdProductInnoveritFragment : Fragment() {
     private lateinit var binding:FragmentListIdProductInnoveritBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
     private val constInnoveritAdapter by lazy { CostInnoveritAdapter(requireContext()) }
    private val viewModel by lazy { ViewModelProvider(requireActivity())[PriceCostInnoveritViewModel::class.java] }
    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListIdProductInnoveritBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)
        val bottomNavigationView =
            activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.visibility = View.GONE
        linearLayoutManager = LinearLayoutManager(requireContext())
        setupRecyclerview()
        viewModelObserver()
    }

    private fun viewModelObserver() {
        viewModel.listIdProduct.observe(viewLifecycleOwner){
            constInnoveritAdapter.submitList(it)
            Log.d("valorquellego",it.toString())
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
        viewModel.getListProduct()
    }
}