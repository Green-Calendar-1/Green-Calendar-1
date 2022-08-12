package com.example.greencalendar10

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class NotificationListAdapter(val context: Context, val notificationList: ArrayList<Notification>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.notification_lv_item, null)

        val profileImg = view.findViewById<ImageView>(R.id.profileImg)
        val notificationContent = view.findViewById<TextView>(R.id.notificationContent)
        val notificationTime = view.findViewById<TextView>(R.id.notificationTime)

        val notification = notificationList[position]
        val resourceId = context.resources.getIdentifier(notification.profileImg, "drawable", context.packageName)
        profileImg.setImageResource(resourceId)
        notificationContent.text = notification.notificationContent
        notificationTime.text = notification.notificationTime

        return view
    }

    override fun getItem(position: Int): Any {
        return notificationList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return notificationList.size
    }


}