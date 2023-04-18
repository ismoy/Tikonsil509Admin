package com.tikonsil.tikonsil509admin.ui.fragment.historysales

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.data.adapter.HistorySalesAdapter
import com.tikonsil.tikonsil509admin.data.local.entity.SalesEntity
import com.tikonsil.tikonsil509admin.data.mapper.toDomain
import com.tikonsil.tikonsil509admin.data.remote.provider.AuthProvider
import com.tikonsil.tikonsil509admin.data.remote.provider.UpdateStatusSalesProvider
import com.tikonsil.tikonsil509admin.data.remote.provider.firebaseApi.FirebaseApi
import com.tikonsil.tikonsil509admin.data.remote.retrofitInstance.Headers
import com.tikonsil.tikonsil509admin.data.remote.retrofitInstance.RetrofitInstance
import com.tikonsil.tikonsil509admin.databinding.FragmentHistorySalesBinding
import com.tikonsil.tikonsil509admin.domain.model.*
import com.tikonsil.tikonsil509admin.presentation.historysales.HistorySalesViewModel
import com.tikonsil.tikonsil509admin.ui.activity.home.HomeActivity
import com.tikonsil.tikonsil509admin.ui.fragment.base.BaseFragment
import com.tikonsil.tikonsil509admin.utils.UtilsView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


@AndroidEntryPoint
class HistorySalesFragment : BaseFragment<FragmentHistorySalesBinding,HistorySalesViewModel>(),SearchView.OnQueryTextListener{
    private var idProductPending:String?=null
    private val historySalesViewModel:HistorySalesViewModel by viewModels()
    @Inject
    lateinit var historySalesAdapter: HistorySalesAdapter
    lateinit var updateStatus:UpdateStatusSalesProvider
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomNavigationView =
            activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.visibility = View.GONE
        shimmerFrameLayout =binding.shimmerHistory
        recyclerView = binding.recyclerviewhistory
        nodata = binding.nodatahistory
        searView = binding.searchViewUser
        cardSearchView = binding.cardSearchViewUser
        searView.setOnQueryTextListener(this)
        updateStatus = UpdateStatusSalesProvider()
        viewModelObserver()
    }

    private fun viewModelObserver() {
        historySalesViewModel.isExistSnapshot.observe(viewLifecycleOwner) { exist ->
            if (exist) {
                shimmerFrameLayout.stopShimmer()
                shimmerFrameLayout.isGone = true
                recyclerView.isGone = true
                nodata.isGone = false
                cardSearchView.isGone = true
            }
        }

        historySalesViewModel.getHistorySales().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                val salesEntityItemList = it.map { entity -> entity.toDomain() }
                historySalesAdapter.submitList(salesEntityItemList)
                historySalesViewModel.insertData(salesEntityItemList)
            }
        }

        historySalesViewModel.readData.observe(viewLifecycleOwner){listHistory->
            setupRecyclerview()
            shimmerFrameLayout.stopShimmer()
            recyclerView.isGone = false
            shimmerFrameLayout.isGone = true
            historySalesAdapter.submitList(listHistory.distinct())

        }
        historySalesAdapter.responseInnoverit.observe(viewLifecycleOwner) { result ->
            idProductPending = UtilsView.getValueSharedPreferences(requireActivity(),"IdProductPendingHistory")
            when {
                result.isSuccess -> {
                    result.getOrNull()?.enqueue(object : Callback<SendRechargeResponse> {
                        override fun onResponse(
                            call: Call<SendRechargeResponse> ,
                            response: Response<SendRechargeResponse>
                        ) {
                            if (response.body()?.status == "success") {
                                Toast.makeText(
                                    requireContext() ,
                                    "La recarga ha sido enviado éxitosamente" ,
                                    Toast.LENGTH_SHORT
                                ).show()
                                sendNotificationToOtherDevice(UtilsView.getValueSharedPreferences(requireActivity(),"TokenUserHistory"))
                                startActivity(Intent(requireContext() , HomeActivity::class.java))
                                requireActivity().finish()
                                updateStatus.updateStatus(UtilsView.getValueSharedPreferences(requireActivity(),"keyIdUpdateStatusHistorySales"),"1")
                                historySalesAdapter.bottomSheetDialog.dismiss()
                            } else {
                                Toast.makeText(
                                    requireContext() ,
                                    "${response.body()?.message}" ,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<SendRechargeResponse> , t: Throwable) {}

                    })
                }
                result.isFailure -> {
                    Toast.makeText(requireContext() , "${result.getOrThrow()}" , Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    }

    private fun setupRecyclerview(){
        recyclerView.adapter = historySalesAdapter
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.hasFixedSize()
    }

    private fun sendNotificationToOtherDevice(token: String?) {
        PushNotification(
            NotificationData(
                requireActivity().getString(R.string.recharge_tikonsil) ,
                requireActivity().getString(R.string.aprobado)
            ) ,
            token!!
        ).also { push ->
            sendNotification(push)

        }
    }

    private fun sendNotification(notification: PushNotification) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response =
                    RetrofitInstance(FirebaseApi.getFSApis().fcm.base_url_fcm).tikonsilApi.postNotification(
                        FirebaseApi.getFSApis().fcm.end_point_fcm ,
                        notification ,
                        Headers.getHeaderFcm()
                    )

                if (!response.isSuccessful) {
                    Toast.makeText(
                        context ,
                        "Error: ${response.errorBody().toString()}" ,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
            }
        }

    override fun getViewModel() =HistorySalesViewModel::class.java

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?)= FragmentHistorySalesBinding.inflate(inflater,container,false)
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
        historySalesViewModel.searchDataBase(searchQuery).observe(viewLifecycleOwner){list->
            list.let {
                historySalesAdapter.submitList(it.distinct()
                )
            }
        }
    }


}