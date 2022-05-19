package com.tikonsil.tikonsil509admin.ui.activity.home

import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.databinding.ActivityHomeBinding
import com.tikonsil.tikonsil509admin.presentation.home.UserViewModel
import com.tikonsil.tikonsil509admin.ui.base.BaseActivity


class HomeActivity : BaseActivity<UserViewModel, ActivityHomeBinding>(),
    NavigationView.OnNavigationItemSelectedListener{
    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //BottomNavigationView
        navController = findNavController(R.id.container_fragment)
        bottomNavigationView = binding.bottomNavigationView
        setupBottomNavigation()
        //DrawerLayout
        drawerLayout = binding.drawerLayout
        //NavigationUpButton
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)

        val navigationview: NavigationView = binding.navView
        navigationview.setNavigationItemSelectedListener(this)
        showDataInView()
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }


    private fun setupBottomNavigation() {
        bottomNavigationView.setupWithNavController(navController)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_RegisterUsers -> navController.navigate(R.id.action_homeFragment_to_registerUsersFragment)
            R.id.nav_historysales->navController.navigate(R.id.action_homeFragment_to_historySalesFragment)
            R.id.nav_change_price_country->navController.navigate(R.id.action_homeFragment_to_countryPriceFragment)

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }






    override fun getActivityBinding() = ActivityHomeBinding.inflate(layoutInflater)
    override fun getViewModel() = UserViewModel::class.java

}