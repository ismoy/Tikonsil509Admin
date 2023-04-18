package com.tikonsil.tikonsil509admin.ui.activity.home

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.data.remote.provider.AuthProvider
import com.tikonsil.tikonsil509admin.databinding.ActivityHomeBinding
import com.tikonsil.tikonsil509admin.presentation.home.UserViewModel
import com.tikonsil.tikonsil509admin.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import javax.inject.Inject


@AndroidEntryPoint
class HomeActivity : BaseActivity<UserViewModel, ActivityHomeBinding>(),
    NavigationView.OnNavigationItemSelectedListener{
    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var drawerLayout: DrawerLayout
    private val userViewModel:UserViewModel by viewModels()
    @Inject
    lateinit var mAuthProvider:AuthProvider
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //BottomNavigationView
        navController = findNavController(R.id.container_fragment)
        bottomNavigationView = binding.bottomNavigationView
        setupBottomNavigation()
        //DrawerLayout
        drawerLayout = binding.drawerLayout
        mAuthProvider = AuthProvider()
        //NavigationUpButton
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)

        val navigationview: NavigationView = binding.navView
        navigationview.setNavigationItemSelectedListener(this)
        viewModelObserver()
    }

    private fun viewModelObserver() {
        userViewModel.getOnlyUser(mAuthProvider.getId().toString())
        userViewModel.responseUser.observe(this) { response ->
            if (response.isSuccessful) {
                binding.root.apply {
                    val headerdrawer = findViewById<NavigationView>(R.id.nav_view)?.getHeaderView(0)
                    val nameheader =  headerdrawer?.findViewById<TextView>(R.id.usernamedrawable)
                    val image_drawable =
                        headerdrawer!!.findViewById<CircleImageView>(R.id.image_drawable)
                    response.body()?.apply {
                        val name = firstname
                        nameheader?.text = "Hola,\n$name"

                        if (image != null) {
                            Glide.with(this@HomeActivity).load(image).into(image_drawable)
                        }
                    }

                }

            } else {
                Toast.makeText(this, response.code(), Toast.LENGTH_SHORT).show()
            }
        }
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
           // R.id.nav_change_bonus_register->navController.navigate(R.id.action_homeFragment_to_changePriceBonusFragment)
            R.id.nav_register_cost_innoverit->navController.navigate(R.id.action_homeFragment_to_registerCostInnoveritFragment)
            R.id.nav_history_error_sales->navController.navigate(R.id.action_homeFragment_to_errorSalesFragment)
            R.id.nav_list_idProduct_innoverit->navController.navigate(R.id.action_homeFragment_to_listIdProductInnoveritFragment)

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }






    override fun getActivityBinding() = ActivityHomeBinding.inflate(layoutInflater)
    override fun getViewModel() = UserViewModel::class.java

}