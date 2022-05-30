package com.tikonsil.tikonsil509admin.ui.fragment.listnotification

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.tikonsil.tikonsil509admin.data.remote.provider.AuthProvider
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.data.adapter.ListNotificationAdapter
import com.tikonsil.tikonsil509admin.domain.repository.listnotification.ListNotificationRepository
import com.tikonsil.tikonsil509admin.presentation.listnotification.ListNotificationViewModel
import com.tikonsil.tikonsil509admin.presentation.listnotification.ListNotificationViewModelProvider
import com.tikonsil.tikonsil509admin.utils.Constant

/** * Created by ISMOY BELIZAIRE on 16/05/2022. */
abstract class ListNotificationValidate<VB:ViewBinding,VM:ViewModel>:Fragment() {
    protected lateinit var binding: VB
    protected lateinit var viewmodel: ListNotificationViewModel
    protected lateinit var mAuthProvider: AuthProvider
    protected lateinit var mConstant: Constant
    //protected lateinit var shimmerFrameLayout: ShimmerFrameLayout
    var recycler: RecyclerView?=null
    private lateinit var listnotificationadapter: ListNotificationAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getFragmentBinding(inflater, container)
        val repository = ListNotificationRepository()
        val factory = ListNotificationViewModelProvider(repository)
        viewmodel = ViewModelProvider(requireActivity(), factory)[ListNotificationViewModel::class.java]
        mAuthProvider = AuthProvider()
        mConstant = Constant()
        listnotificationadapter = ListNotificationAdapter(requireContext())
        linearLayoutManager = LinearLayoutManager(requireContext())
        // shimmerFrameLayout =binding.root.findViewById(R.id.shimmer_history)
        recycler =binding.root.findViewById(R.id.recyclerview_list_notification)
        return binding.root
    }
    fun observelistnotification(){
        viewmodel.getListNotification().observe(viewLifecycleOwner, Observer {
                Log.d("LLLEGOLALISTA",it.toString())
                listnotificationadapter.setListDataNotification(it)
              setupRecyclerview()

        })
    }
    private fun setupRecyclerview(){
        recycler?.adapter = listnotificationadapter
        recycler?.layoutManager = linearLayoutManager
        recycler?.hasFixedSize()
    }
    abstract fun getViewModel(): Class<VM>
    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): VB
}