package com.tikonsil.tikonsil509admin.ui.fragment.historysales

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.facebook.shimmer.ShimmerFrameLayout
import com.tikonsil.tikonsil509admin.data.remote.provider.AuthProvider
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.data.adapter.HistorySalesAdapter
import com.tikonsil.tikonsil509admin.domain.repository.historysales.HistorySalesRepository
import com.tikonsil.tikonsil509admin.presentation.historysales.HistorySalesViewModel
import com.tikonsil.tikonsil509admin.presentation.historysales.HistorySalesViewModelProvider
import com.tikonsil.tikonsil509admin.utils.Constant

/** * Created by ISMOY BELIZAIRE on 07/05/2022. */
abstract class ValidateSaleHistory<VB : ViewBinding, VM : ViewModel> : Fragment()  {
 protected lateinit var binding: VB
 protected lateinit var viewmodel: HistorySalesViewModel
 protected lateinit var mAuthProvider: AuthProvider
 protected lateinit var mConstant: Constant
 protected lateinit var shimmerFrameLayout: ShimmerFrameLayout
 var recycler: RecyclerView?=null
 lateinit var nodata: ImageView
 protected lateinit var historySalesAdapter: HistorySalesAdapter
 private lateinit var linearLayoutManager: LinearLayoutManager

 override fun onCreateView(
  inflater: LayoutInflater,
  container: ViewGroup?,
  savedInstanceState: Bundle?): View? {
  binding = getFragmentBinding(inflater, container)
  viewmodel = ViewModelProvider(
   requireActivity())[HistorySalesViewModel::class.java]
  mAuthProvider = AuthProvider()
  mConstant = Constant()
  historySalesAdapter = HistorySalesAdapter(requireActivity())
  linearLayoutManager = LinearLayoutManager(requireContext())
  shimmerFrameLayout =binding.root.findViewById(R.id.shimmer_history)
  recycler =binding.root.findViewById(R.id.recyclerviewhistory)
  nodata =binding.root.findViewById(R.id.nodatahistory)
  setupRecyclerview()
  return binding.root
 }

 fun observehistorysales(){

  viewmodel.isExistSnapshot.observe(viewLifecycleOwner, Observer { exist->
   if (exist){
    shimmerFrameLayout.stopShimmer()
    shimmerFrameLayout.isGone=true
    recycler?.isGone=true
    nodata.isGone=false
   }
  })
  viewmodel.getHistorySales().observe(viewLifecycleOwner, Observer {
   if (it.isNotEmpty()){
     shimmerFrameLayout.stopShimmer()
    recycler?.isGone=false
    shimmerFrameLayout.isGone=true
    historySalesAdapter.submitList(it)
   }

  })
 }
 private fun setupRecyclerview(){
  recycler?.adapter = historySalesAdapter
  recycler?.layoutManager = linearLayoutManager
  recycler?.hasFixedSize()
 }
 abstract fun getViewModel(): Class<VM>
 abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): VB
}