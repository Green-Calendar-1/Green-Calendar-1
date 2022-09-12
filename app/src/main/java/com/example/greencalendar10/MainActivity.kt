package com.example.greencalendar10

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.greencalendar10.databinding.ActivityLoginBinding
import com.example.greencalendar10.databinding.ActivityMainBinding
import com.example.greencalendar10.model.User
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intentToBoard = Intent(this,BoardActivity::class.java)


        // toolbar
        val toolbar: Toolbar = findViewById(R.id.main_toolbar)
        toolbar.inflateMenu(R.menu.main_toolbar_menu)

        // 하단 바 바인딩
        val bnv_main = findViewById(R.id.bottomNavigationView) as BottomNavigationView


        bnv_main.run { setOnItemSelectedListener {
            when(it.itemId) {

                R.id.homeMenu -> {
                    val homeFragment = HomeFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.flFragment, homeFragment).commit()
                }
                R.id.categoryMenu -> {
                    //val categoryFragment = CategoryFragment()
                    //supportFragmentManager.beginTransaction().replace(R.id.flFragment, categoryFragment).commit()

                    val boardFragment = BoardFragment()
                    //supportFragmentManager.beginTransaction().replace(R.id.flFragment,boardFragment).commit()
                    startActivity(intentToBoard)
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


        // toolbar 알림창 -> icon 임시
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
    fun changeFragment(index: Int){
        when(index){
            1 -> {
                val commentListFragment = CommentListFragment()
                supportFragmentManager.beginTransaction().
                replace(R.id.flFragment, commentListFragment).commit()
            }
        }
    }

}