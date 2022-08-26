package com.example.greencalendar10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.widget.Toolbar

class NotificationList : AppCompatActivity() {

    // test ..
    var notificationList = arrayListOf<Notification>(
        Notification("안영준님이 댓글을 남겼습니다","3분 전", "profile_temp"),
        Notification("윤현조님이 댓글을 남겼습니다","5분 전", "profile_temp"),
        Notification("윤현조님이 댓글을 남겼습니다","10분 전", "profile_temp")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_list)

        val notificationAdapter = NotificationListAdapter(this, notificationList)

        val notificationListView: ListView = findViewById(R.id.notificationListView)
        notificationListView.adapter = notificationAdapter

        val actionbar = supportActionBar
        actionbar!!.title = "New Activity"
        actionbar.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}