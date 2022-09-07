package com.example.greencalendar10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        //splash화면을 꺼줌
        Handler().postDelayed({

            startActivity(Intent(this,LoginActivity::class.java)) //<<이동하고 싶은 액티비티
            finish()
        },1500)//1.5초뒤에에


    }}