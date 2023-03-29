package com.tikonsil.tikonsil509admin.ui.fragment.errorSales

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.data.adapter.ErrorSalesAdapter
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
import com.tikonsil.tikonsil509admin.utils.UtilsView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ErrorSalesFragment : Fragment() {
    private lateinit var binding: FragmentErrorSalesBinding
    private val errorSalesAdapter by lazy { ErrorSalesAdapter(requireActivity()) }
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val viewModel by lazy { ViewModelProvider(requireActivity())[HistorySalesViewModel::class.java] }
    private val updateStatus by lazy { UpdateStatusSalesProvider() }
    private var idProductPending:String?=null
    private lateinit var mAuthProvider: AuthProvider
    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentErrorSalesBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)
        val bottomNavigationView =
            activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.visibility = View.GONE
        linearLayoutManager = LinearLayoutManager(requireContext())
        mAuthProvider = AuthProvider()
        setupRecyclerview()
        observerViewModel()

    }

    private fun observerViewModel() {
        viewModel.getErrorSales.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                with(binding) {
                    shimmerHistory.stopShimmer()
                    recyclerviewhistory.isGone = false
                    shimmerHistory.isGone = true
                }
                errorSalesAdapter.submitList(it)
            }
        }
        errorSalesAdapter.responseInnoverit.observe(viewLifecycleOwner) { result ->
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
                                    errorSalesAdapter.bottomSheetDialog.dismiss()
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

    private fun updatePendingSales() {
        updateStatus.updateStatus(UtilsView.getValueSharedPreferences(requireActivity(),"IdUserPending") ,"1")
    }

    private fun setupRecyclerview() {
        with(binding) {
            recyclerviewhistory.adapter = errorSalesAdapter
            recyclerviewhistory.layoutManager = linearLayoutManager
            recyclerviewhistory.hasFixedSize()
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.historyErrorSales()
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
}