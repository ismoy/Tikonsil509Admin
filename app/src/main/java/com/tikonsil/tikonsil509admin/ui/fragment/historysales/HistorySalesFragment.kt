package com.tikonsil.tikonsil509admin.ui.fragment.historysales

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.data.remote.provider.AuthProvider
import com.tikonsil.tikonsil509admin.data.remote.provider.UpdateStatusSalesProvider
import com.tikonsil.tikonsil509admin.data.remote.provider.firebaseApi.FirebaseApi
import com.tikonsil.tikonsil509admin.data.remote.retrofitInstance.Headers
import com.tikonsil.tikonsil509admin.data.remote.retrofitInstance.RetrofitInstance
import com.tikonsil.tikonsil509admin.databinding.FragmentHistorySalesBinding
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


class HistorySalesFragment : ValidateSaleHistory<FragmentHistorySalesBinding,HistorySalesViewModel>(){
    private var idProductPending:String?=null
    private val updateStatus by lazy { UpdateStatusSalesProvider() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomNavigationView =
            activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.visibility = View.GONE
        observehistorysales()
        viewModelObserver()
    }

    private fun viewModelObserver() {
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


}