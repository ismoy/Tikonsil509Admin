package com.tikonsil.tikonsil509admin.ui.fragment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.card.MaterialCardView
import com.tikonsil.tikonsil509admin.data.adapter.ErrorSalesAdapter
import com.tikonsil.tikonsil509admin.data.adapter.LastSaleAdapter
import com.tikonsil.tikonsil509admin.data.remote.provider.AuthProvider
import com.tikonsil.tikonsil509admin.data.remote.provider.TokenProvider
import com.tikonsil.tikonsil509admin.data.remote.provider.UserProvider
import com.tikonsil.tikonsil509admin.domain.repository.login.LoginRepository
import com.tikonsil.tikonsil509admin.presentation.costInnoverit.PriceCostInnoveritViewModel
import com.tikonsil.tikonsil509admin.presentation.login.LoginViewModel
import com.tikonsil.tikonsil509admin.presentation.login.LoginViewModelFactory
import com.tikonsil.tikonsil509admin.utils.Constant


abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : Fragment() {
    lateinit var navController: NavController
    lateinit var progressbar: ProgressBar
    lateinit var binding: VB
    lateinit var lastSaleAdapter: LastSaleAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var recyclerView: RecyclerView
    lateinit var mConstant: Constant
    lateinit var mUserProvider: UserProvider
    lateinit var searView: SearchView
    lateinit var shimmerFrameLayout: ShimmerFrameLayout
    lateinit var nodata: ImageView
    lateinit var historySalesErrorAdapter:ErrorSalesAdapter
    lateinit var cardSearchView: MaterialCardView
    lateinit var loginViewModel:LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getFragmentBinding(inflater, container)
        lastSaleAdapter = LastSaleAdapter(requireContext())
        linearLayoutManager = LinearLayoutManager(requireContext())
        mConstant = Constant()
        mUserProvider = UserProvider()
        historySalesErrorAdapter = ErrorSalesAdapter(requireActivity())
        val repositoryLogin = LoginRepository()
        val factoryLogin = LoginViewModelFactory(repositoryLogin)
        loginViewModel = ViewModelProvider(requireActivity(), factoryLogin)[LoginViewModel::class.java]
        return binding.root
    }

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): VB
    abstract fun getViewModel(): Class<VM>
}