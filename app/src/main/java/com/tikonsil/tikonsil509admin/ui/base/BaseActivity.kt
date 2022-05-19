package com.tikonsil.tikonsil509admin.ui.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.navigation.NavigationView
import com.tikonsil.tikonsil509.data.remote.provider.AuthProvider
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.domain.repository.login.LoginRepository
import com.tikonsil.tikonsil509admin.presentation.home.UserViewModel
import com.tikonsil.tikonsil509admin.presentation.login.LoginViewModelFactory

/** * Created by ISMOY BELIZAIRE on 23/04/2022. */
abstract class BaseActivity<VM : ViewModel, VB : ViewBinding> : AppCompatActivity() {
    protected lateinit var binding: VB
    protected lateinit var viewmodel: UserViewModel
    protected lateinit var mAuthProvider: AuthProvider

    protected lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getActivityBinding()
        setContentView(binding.root)
        val repository = LoginRepository()
        val factory = LoginViewModelFactory(repository)
        viewmodel = ViewModelProvider(this, factory)[UserViewModel::class.java]
        mAuthProvider = AuthProvider()
    }

    protected abstract fun getActivityBinding(): VB
    protected abstract fun getViewModel(): Class<VM>


    @SuppressLint("SetTextI18n")
    fun showDataInView() {
        viewmodel.getOnlyUser(mAuthProvider.getId().toString())
        viewmodel.ResposeUsers.observe(this, Observer { response ->
            if (response.isSuccessful) {
                binding.root.apply {
                    val headerdrawer = findViewById<NavigationView>(R.id.nav_view)?.getHeaderView(0)
                    val nameheader = headerdrawer?.findViewById<TextView>(R.id.usernamedrawable)
                    val name = response.body()?.firstname
                    nameheader?.text = "Hola,\n$name"

                }


            } else {
                Toast.makeText(this, response.code(), Toast.LENGTH_SHORT).show()
            }
        })
    }

}