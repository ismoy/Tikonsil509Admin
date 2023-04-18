package com.tikonsil.tikonsil509admin.ui.fragment.errorSales

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.data.adapter.ErrorSalesAdapter
import com.tikonsil.tikonsil509admin.data.mapper.toDomain
import com.tikonsil.tikonsil509admin.data.mapper.toSalesErrorDomain
import com.tikonsil.tikonsil509admin.data.remote.provider.AuthProvider
import com.tikonsil.tikonsil509admin.data.remote.provider.UpdateStatusSalesProvider
import com.tikonsil.tikonsil509admin.data.remote.provider.firebaseApi.FirebaseApi
import com.tikonsil.tikonsil509admin.data.remote.retrofitInstance.Headers
import com.tikonsil.tikonsil509admin.data.remote.retrofitInstance.RetrofitInstance
import com.tikonsil.tikonsil509admin.databinding.FragmentErrorSalesBinding
import com.tikonsil.tikonsil509admin.domain.model.NotificationData
import com.tikonsil.tikonsil509admin.domain.model.PushNotification
import com.tikonsil.tikonsil509admin.domain.model.SendRechargeResponse
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
import javax.inject.Inject

@AndroidEntryPoint
class ErrorSalesFragment :BaseFragment<FragmentErrorSalesBinding,HistorySalesViewModel>(),
    SearchView.OnQueryTextListener {
    private var idProductPending:String?=null
    private val historySalesViewModel:HistorySalesViewModel by viewModels()
    @Inject
    lateinit var updateStatus:UpdateStatusSalesProvider


    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)
        val bottomNavigationView =
            activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.visibility = View.GONE
        recyclerView = binding.recyclerviewhistoryerror
        shimmerFrameLayout = binding.shimmerError
        nodata = binding.nodataerror
        cardSearchView = binding.cardSearchViewError
        searView = binding.searchViewError
        searView.setOnQueryTextListener(this)
        updateStatus = UpdateStatusSalesProvider()
        setupRecyclerview()
        observerViewModel()

    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentErrorSalesBinding=FragmentErrorSalesBinding.inflate(inflater,container,false)

    override fun getViewModel() = HistorySalesViewModel::class.java



    private fun observerViewModel() {
        historySalesViewModel.getErrorSales.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                val salesErrorEntityItemList = it.map { entity -> entity.toSalesErrorDomain() }
                historySalesErrorAdapter.submitList(salesErrorEntityItemList)
                historySalesViewModel.insertSalesError(salesErrorEntityItemList)
            }
        }

        historySalesViewModel.readSalesError.observe(viewLifecycleOwner){listHistory->
            shimmerFrameLayout.stopShimmer()
            recyclerView.isGone = false
            shimmerFrameLayout.isGone = true
            historySalesErrorAdapter.submitList(listHistory)

        }


        historySalesErrorAdapter.responseInnoverit.observe(viewLifecycleOwner) { result ->
            idProductPending =UtilsView.getValueSharedPreferences(requireActivity(),"IdProductPending")
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
                                    sendNotificationToOtherDevice(UtilsView.getValueSharedPreferences(requireActivity(),"TokenUser"))
                                    startActivity(Intent(requireContext() , HomeActivity::class.java))
                                    requireActivity().finish()
                                    updateStatus.updateStatusErrorSales(UtilsView.getValueSharedPreferences(requireActivity(),"keyIdUpdateStatusErrorSales"),"1")
                                    updatePendingSales()
                                    historySalesErrorAdapter.bottomSheetDialog.dismiss()
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
        historySalesViewModel.isExistSnapshotError.observe(viewLifecycleOwner){exist->
            if (exist){
                nodata.visibility =View.VISIBLE
                shimmerFrameLayout.stopShimmer()
                shimmerFrameLayout.isGone = true
                cardSearchView.isGone = true
            }

        }
    }

    private fun updatePendingSales() {
        updateStatus.updateStatus(UtilsView.getValueSharedPreferences(requireActivity(),"IdUserPending") ,"1")
    }

    private fun setupRecyclerview() {
            recyclerView.adapter = historySalesErrorAdapter
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.hasFixedSize()

    }

    override fun onResume() {
        super.onResume()
        historySalesViewModel.historyErrorSales()
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
        historySalesViewModel.searchSalesError(searchQuery).observe(viewLifecycleOwner){list->
            list.let {
                historySalesErrorAdapter.submitList(it)
            }
        }
    }
}