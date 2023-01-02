package com.tikonsil.tikonsil509admin.ui.fragment.registered_users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.data.adapter.RegistreredUserAdapter
import com.tikonsil.tikonsil509admin.domain.repository.registrereduser.RegistreredUserRepository
import com.tikonsil.tikonsil509admin.presentation.registereduser.RegisteredUserViewModel
import com.tikonsil.tikonsil509admin.presentation.registereduser.RegisteredUserViewModelProvider

/** * Created by ISMOY BELIZAIRE on 13/05/2022. */
abstract class RegistreredUserValidate<VM:ViewModel,VB:ViewBinding>:Fragment() {
    protected lateinit var binding:VB
    protected lateinit var viewmodel: RegisteredUserViewModel
    protected lateinit var recyclerview:RecyclerView
    protected  val registreredusersadapter by lazy { RegistreredUserAdapter(requireContext()) }
    protected lateinit var linearLayoutManager:LinearLayoutManager
    private lateinit var noDataFound:ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getFragmentBinding(inflater, container)
        val repository =RegistreredUserRepository()
        val factory =RegisteredUserViewModelProvider(repository)
        viewmodel =ViewModelProvider(requireActivity(),factory)[RegisteredUserViewModel::class.java]
        recyclerview =binding.root.findViewById(R.id.recyclerviewregisteruser)
        noDataFound =binding.root.findViewById(R.id.noDataFound)
        linearLayoutManager = LinearLayoutManager(requireContext())
        setupRecyclerview()
        return binding.root
    }

    fun observeData(){

        viewmodel.isExistSnapshot.observe(viewLifecycleOwner, Observer { exist->
            if (exist){
                noDataFound.isGone=false
            }
        })
        viewmodel.getRegistreredUsers().observe(requireActivity(), Observer {
            if (it!=null){
                registreredusersadapter.setsaleListData(it)
            }else{
                Toast.makeText(requireContext(), "lista vacia", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun setupRecyclerview(){
        recyclerview.adapter = registreredusersadapter
        recyclerview.layoutManager = linearLayoutManager
        recyclerview.hasFixedSize()
    }
    abstract fun getViewModel():Class<VM>
    abstract fun getFragmentBinding(inflater: LayoutInflater,container: ViewGroup?):VB
}