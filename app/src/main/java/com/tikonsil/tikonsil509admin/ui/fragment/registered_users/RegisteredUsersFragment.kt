package com.tikonsil.tikonsil509admin.ui.fragment.registered_users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.compose.ui.text.toLowerCase
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.data.adapter.ListUsersAdapter
import com.tikonsil.tikonsil509admin.databinding.FragmentRegisteredUsersBinding
import com.tikonsil.tikonsil509admin.domain.model.Users
import com.tikonsil.tikonsil509admin.presentation.home.UserViewModel
import com.tikonsil.tikonsil509admin.ui.fragment.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class RegisteredUsersFragment : BaseFragment<FragmentRegisteredUsersBinding,UserViewModel>() {

   private val listUsersAdapter by lazy { ListUsersAdapter(requireContext()) }
    private  val userViewModel:UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomNavigationView =
            activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.visibility = View.GONE
        recyclerView =binding.recyclerviewregisteruser
        searView = binding.searchViewUser
        viewModelObserver()
    }

    private fun filterByName(users: ArrayList<Users>) {
        searView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(textFilter: String?): Boolean {
                if (textFilter!!.isEmpty()){
                    listUsersAdapter.setSaleListData(users)
                }else{
                    listUsersAdapter.setSaleListData(users)
                    val userFiltered = listUsersAdapter.userregistrered.filter { yes-> yes.firstname!!.lowercase(
                        Locale.ROOT
                    )
                        .contains(textFilter.toString()) ||
                            yes.lastname!!.lowercase(Locale.ROOT).contains(textFilter.lowercase(Locale.ROOT)) || yes.phone!!.contains(textFilter)}
                    listUsersAdapter.filterListUser(userFiltered)
                }

                return true
            }

        })
    }


    private fun viewModelObserver() {
        userViewModel.getUsersResponse.observe(viewLifecycleOwner){
            if (it!=null){
                setupRecyclerview()
                listUsersAdapter.setSaleListData(it as ArrayList<Users>)
                filterByName(it)

            }
        }
        userViewModel.isExistSnapshot.observe(viewLifecycleOwner){ exist->
            if (exist){
                binding.noDataFound.isGone=false
            }
        }
    }


    override fun getViewModel() =UserViewModel::class.java
    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?)= FragmentRegisteredUsersBinding.inflate(inflater,container,false)

    private fun setupRecyclerview(){
        recyclerView.adapter = listUsersAdapter
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.hasFixedSize()
    }

    override fun onResume() {
        super.onResume()
        userViewModel.getUsers()
    }
}