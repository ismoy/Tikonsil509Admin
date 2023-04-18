package com.tikonsil.tikonsil509admin.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.data.remote.provider.AuthProvider
import com.tikonsil.tikonsil509admin.data.remote.provider.TokenProvider
import com.tikonsil.tikonsil509admin.databinding.FragmentHomeBinding
import com.tikonsil.tikonsil509admin.domain.model.BalanceResponse
import com.tikonsil.tikonsil509admin.presentation.costInnoverit.PriceCostInnoveritViewModel
import com.tikonsil.tikonsil509admin.presentation.historysales.HistorySalesViewModel
import com.tikonsil.tikonsil509admin.presentation.home.UserViewModel
import com.tikonsil.tikonsil509admin.ui.fragment.base.BaseFragment
import com.tikonsil.tikonsil509admin.utils.UtilsView
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, UserViewModel>() {

    private  val userViewModel:UserViewModel by viewModels()
    private lateinit var shimmerFrameLayoutWelcome: ShimmerFrameLayout
    private val historySalesViewModel:HistorySalesViewModel by viewModels()
    private val priceCostViewModel:PriceCostInnoveritViewModel by viewModels()
    @Inject
    lateinit var mTokenProvider:TokenProvider
    lateinit var authProvider: AuthProvider
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
        super.onViewCreated(view, savedInstanceState)
        progressbar = binding.root.findViewById(R.id.progressbar)
        shimmerFrameLayout =binding.root.findViewById(R.id.shimmer)
        shimmerFrameLayoutWelcome =binding.root.findViewById(R.id.shimmerwelcome)
        historySalesViewModel.deleteAll()
        historySalesViewModel.deleteAllSalesError()
        priceCostViewModel.deleteAllCost()
        authProvider = AuthProvider()
        mTokenProvider =TokenProvider()
        generateToken()
        viewModelObserver()

    }

    private fun viewModelObserver() {
        binding.root.apply {
            val userNameWelcome = findViewById<TextView>(R.id.usernamewel)
            val userImageWelcome = findViewById<ImageView>(R.id.image_home)
            val totalBalance = findViewById<TextView>(R.id.totalbalance)
            val totalSales = findViewById<TextView>(R.id.totalventasagent)
            val totalUsers = findViewById<TextView>(R.id.registredusers)
            val recyclerView = findViewById<RecyclerView>(R.id.recyclerviewultimaventa)
            val noDataFound = findViewById<ImageView>(R.id.nodata)
            userViewModel.responseUser.observe(viewLifecycleOwner) { userData ->
                progressbar.isGone = false
                if (userData.isSuccessful) {
                    userData.body()?.apply {
                        userNameWelcome.text = firstname
                        if (image != null) {
                            Glide.with(requireActivity()).load(image).into(userImageWelcome)
                            progressbar.isGone = true
                        }
                    }
                    shimmerFrameLayoutWelcome.stopShimmer()
                    shimmerFrameLayoutWelcome.isGone = true
                    userNameWelcome!!.isVisible = true
                    binding.root.findViewById<CircleImageView>(R.id.image).isVisible = true
                    binding.root.findViewById<RelativeLayout>(R.id.relativebalance).isGone = false
                }
            }
            userViewModel.getBalanceResponse.observe(viewLifecycleOwner) { responseBalance ->
                totalBalance.text = responseBalance
            }
            userViewModel.lastSalesResponse.observe(viewLifecycleOwner) { lastSale ->
                if (lastSale.isNotEmpty()){
                    lastSaleAdapter.setsaleListData(lastSale)
                    setupRecyclerview(recyclerView)
                    shimmerFrameLayout.stopShimmer()
                    shimmerFrameLayout.isGone=true
                    recyclerView?.isGone=false
                }
             totalSales.text = lastSale.size.toString()
            }
            userViewModel.getUsersResponse.observe(viewLifecycleOwner){responseUser->
                totalUsers.text = responseUser.size.toString()
            }
            userViewModel.isExistSnapshot.observe(viewLifecycleOwner){notExist->
                if (notExist){
                    noDataFound.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupRecyclerview(recyclerView: RecyclerView) {
        recyclerView.adapter = lastSaleAdapter
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.hasFixedSize()
    }

    private fun generateToken() {
        mTokenProvider.createToken(authProvider.getId().toString())
    }

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) =FragmentHomeBinding.inflate(inflater,container,false)
    override fun getViewModel() = UserViewModel::class.java

    override fun onResume() {
        super.onResume()
        userViewModel.getOnlyUser(authProvider.getId().toString())
        userViewModel.getBalance()
        userViewModel.getLastSales()
        userViewModel.getUsers()
        UtilsView.clearSharedPreferences(requireContext(),"sharedPreferences")
        val bottomNavigationView =
            activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.visibility = View.VISIBLE
    }

}