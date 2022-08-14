package com.example.greencalendar10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import com.example.greencalendar10.databinding.ActivityLoginBinding
import com.example.greencalendar10.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 하단 바 바인딩
        val bnv_main = findViewById(R.id.bottomNavigationView) as BottomNavigationView

        bnv_main.run { setOnItemSelectedListener {
            when(it.itemId) {

                R.id.homeMenu -> {
                    val homeFragment = HomeFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.flFragment, homeFragment).commit()
                }
                R.id.categoryMenu -> {
                    val categoryFragment = CategoryFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.flFragment, categoryFragment).commit()
                }
                R.id.myDiaryMenu -> {
                    val myDiaryFragment = MyDiaryFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.flFragment, myDiaryFragment).commit()
                }
                R.id.myPageMenu -> {
                    val myPageFragment = MyPageFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.flFragment, myPageFragment).commit()
                }
            }
            true
        }
            selectedItemId = R.id.homeFragment
        }

        // toolbar
        val toolbar: Toolbar = findViewById(R.id.main_toolbar)
        toolbar.inflateMenu(R.menu.main_toolbar_menu)

        toolbar.setOnMenuItemClickListener{
            when(it.itemId) {
                R.id.action_notification -> {
                    val intentToNotification = Intent(this, NotificationList::class.java)
                    startActivity(intentToNotification)
                }
            }
            true
        }
    }





}