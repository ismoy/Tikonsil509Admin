package com.tikonsil.tikonsil509admin.ui.activity.detailsnotification

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.domain.model.NotificationList
import com.tikonsil.tikonsil509admin.ui.fragment.detailsnotification.DetailsNotificationFragment

class DetailsNotification : AppCompatActivity() {
    private lateinit var recieverdata: NotificationList
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_notification)


        //inflate view in Fragment reference
       /* val fragment: Fragment = DetailsNotificationFragment()
        val manager = (this as AppCompatActivity).supportFragmentManager
        manager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .commit()*/
    }
}