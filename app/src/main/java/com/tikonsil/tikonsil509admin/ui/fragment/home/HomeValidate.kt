package com.tikonsil.tikonsil509admin.ui.fragment.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.facebook.shimmer.ShimmerFrameLayout
import com.tikonsil.tikonsil509admin.data.remote.provider.AuthProvider
import com.tikonsil.tikonsil509.data.remote.provider.TokenProvider
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.data.adapter.LastSaleAdapter
import com.tikonsil.tikonsil509admin.domain.repository.home.UsersRepository
import com.tikonsil.tikonsil509admin.domain.repository.lastsales.LastSalesRepository
import com.tikonsil.tikonsil509admin.domain.repository.totalnotification.NotificationCountRepository
import com.tikonsil.tikonsil509admin.domain.repository.totalsales.TotalSalesRepository
import com.tikonsil.tikonsil509admin.domain.repository.totaluser.TotalUserRepository
import com.tikonsil.tikonsil509admin.presentation.home.UserViewModel
import com.tikonsil.tikonsil509admin.presentation.home.UserViewModelFactory
import com.tikonsil.tikonsil509admin.presentation.lastsales.LastSalesViewModel
import com.tikonsil.tikonsil509admin.presentation.lastsales.LastSalesViewModelFactory
import com.tikonsil.tikonsil509admin.presentation.totalnotification.NotificationCountViewModeProvider
import com.tikonsil.tikonsil509admin.presentation.totalnotification.NotificationCountViewModel
import com.tikonsil.tikonsil509admin.presentation.totalsales.TotalSalesViewModel
import com.tikonsil.tikonsil509admin.presentation.totalsales.TotalSalesViewModelFactory
import com.tikonsil.tikonsil509admin.presentation.totaluser.TotalUserViewModel
import com.tikonsil.tikonsil509admin.presentation.totaluser.TotalUserViewModelFactory
import de.hdodenhof.circleimageview.CircleImageView

/** * Created by ISMOY BELIZAIRE on 26/04/2022. */
abstract class HomeValidate<VB:ViewBinding,VM:ViewModel>:Fragment() {
 protected lateinit var binding:VB
 protected lateinit var viewmodel: UserViewModel
 protected lateinit var mAuthProvider: AuthProvider
 protected lateinit var navController: NavController
 private lateinit var lastSaleAdapter: LastSaleAdapter
 private lateinit var linearLayoutManager: LinearLayoutManager
 protected var shimmerFrameLayout: ShimmerFrameLayout?=null
 protected var shimmerFrameLayoutwelcome: ShimmerFrameLayout?=null
 protected var usernamewel:TextView?=null
 protected  var recycler:RecyclerView?=null
 protected lateinit var mTokenProvider: TokenProvider
 protected lateinit var mviewmodellastsales:LastSalesViewModel
 protected lateinit var mtotaluserviemodel:TotalUserViewModel
 protected lateinit var registredusers:TextView
 protected lateinit var mtotalsalesviewmodel:TotalSalesViewModel
 protected lateinit var totalventasagent:TextView
 protected lateinit var notificationCountViewModel: NotificationCountViewModel
 protected lateinit var txtcount:TextView

 override fun onCreateView(
  inflater: LayoutInflater,
  container: ViewGroup?,
  savedInstanceState: Bundle?
 ): View? {
  binding = getFragmentBinding(inflater, container)
  val repository = UsersRepository()
  val factory = UserViewModelFactory(repository)
  viewmodel = ViewModelProvider(requireActivity(),factory)[UserViewModel::class.java]
    mAuthProvider = AuthProvider()
  lastSaleAdapter = LastSaleAdapter(requireContext())
  linearLayoutManager = LinearLayoutManager(requireContext())
  recycler =binding.root.findViewById(R.id.recyclerviewultimaventa)
  shimmerFrameLayout =binding.root.findViewById(R.id.shimmer)
  shimmerFrameLayoutwelcome =binding.root.findViewById(R.id.shimmerwelcome)
  usernamewel = binding.root.findViewById(R.id.usernamewel)
  mTokenProvider = TokenProvider()
  val repositorylastsales =LastSalesRepository()
  val factorylastsales =LastSalesViewModelFactory(repositorylastsales)
  mviewmodellastsales = ViewModelProvider(requireActivity(),factorylastsales)[LastSalesViewModel::class.java]
  val repositorytotaluser =TotalUserRepository()
  val factorytotaluser =TotalUserViewModelFactory(repositorytotaluser)
  mtotaluserviemodel =ViewModelProvider(requireActivity(),factorytotaluser)[TotalUserViewModel::class.java]
  registredusers =binding.root.findViewById(R.id.registredusers)
  val repositorytotalsales = TotalSalesRepository()
  val factorytotalsales =TotalSalesViewModelFactory(repositorytotalsales)
  mtotalsalesviewmodel =ViewModelProvider(requireActivity(),factorytotalsales)[TotalSalesViewModel::class.java]
  totalventasagent =binding.root.findViewById(R.id.totalventasagent)
  val repositorynotificationcount =NotificationCountRepository()
  val factorynotificationcount = NotificationCountViewModeProvider(repositorynotificationcount)
  notificationCountViewModel =ViewModelProvider(requireActivity(),factorynotificationcount)[NotificationCountViewModel::class.java]
  txtcount =binding.root.findViewById(R.id.txtcount)
  return binding.root
 }


 @SuppressLint("SetTextI18n")
 fun showDataInView(){
  viewmodel.getOnlyUser(mAuthProvider.getId().toString())
  viewmodel.ResposeUsers.observe(viewLifecycleOwner, Observer { response->
   if (response.isSuccessful){
    response.body()?.apply {
     usernamewel?.text =firstname
    }
    shimmerFrameLayoutwelcome?.stopShimmer()
    shimmerFrameLayoutwelcome?.isGone=true
    usernamewel!!.isVisible = true
    binding.root.findViewById<CircleImageView>(R.id.image).isVisible = true
    binding.root.findViewById<RelativeLayout>(R.id.relativebalance).isGone = false
    binding.root.findViewById<ScrollView>(R.id.scrollviewcard).isVisible = true



   }else{
    Toast.makeText(requireContext(), response.code(), Toast.LENGTH_SHORT).show()
   }
  })
 }

 fun observeData(){
  mviewmodellastsales.getLastSales().observe(requireActivity(),
   Observer {
    if (it.isNotEmpty()){
     lastSaleAdapter.setsaleListData(it)
     setupRecyclerview()
     shimmerFrameLayout?.stopShimmer()
     shimmerFrameLayout?.isGone=true
     recycler?.isGone=false
     binding.root.findViewById<TextView>(R.id.nodata).isGone=true
    }else{
     binding.root.findViewById<TextView>(R.id.nodata).isGone=false
     shimmerFrameLayout?.isGone=true
    }
   })
 }
 fun observedatatotaluser(){
  mtotaluserviemodel.getTotalUser().observe(viewLifecycleOwner, Observer {
    registredusers.text=it.size.toString()
  })
 }
 fun observedatatotalsales(){
  mtotalsalesviewmodel.getTotalSales().observe(viewLifecycleOwner, Observer {
    totalventasagent.text =it.size.toString()

  })
 }

 fun observeNotificationCount(){
  notificationCountViewModel.getNotificationCount().observe(viewLifecycleOwner, Observer {
   if (it.size!=0) {
   txtcount.text = it.size.toString()
   }
  })
 }
 abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): VB

 abstract fun getViewModel():Class<VM>
 private fun setupRecyclerview(){
  recycler!!.adapter = lastSaleAdapter
  recycler!!.layoutManager = linearLayoutManager
  recycler!!.hasFixedSize()
 }
 fun observerListNotification(){
  navController.navigate(R.id.action_homeFragment_to_listNotificationFragment)
 }

}