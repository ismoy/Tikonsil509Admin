package com.tikonsil.tikonsil509admin.ui.activity.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tikonsil.tikonsil509admin.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}