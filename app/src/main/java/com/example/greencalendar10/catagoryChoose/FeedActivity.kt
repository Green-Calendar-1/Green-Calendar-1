package com.example.greencalendar10.catagoryChoose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.greencalendar10.R
import com.example.greencalendar10.databinding.ActivityFeedBinding

class FeedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        val binding = ActivityFeedBinding.inflate(layoutInflater)

        setContentView(binding.root)




        setContentView(R.layout.activity_feed)
    }





}