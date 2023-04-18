package com.tikonsil.tikonsil509admin.ui.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.tikonsil.tikonsil509admin.data.remote.provider.AuthProvider
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.domain.repository.login.LoginRepository
import com.tikonsil.tikonsil509admin.presentation.home.UserViewModel
import com.tikonsil.tikonsil509admin.presentation.login.LoginViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView

/** * Created by ISMOY BELIZAIRE on 23/04/2022. */
abstract class BaseActivity<VM : ViewModel, VB : ViewBinding> : AppCompatActivity() {
    protected lateinit var binding: VB
    protected lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getActivityBinding()
        setContentView(binding.root)
    }

    protected abstract fun getActivityBinding(): VB
    protected abstract fun getViewModel(): Class<VM>


}