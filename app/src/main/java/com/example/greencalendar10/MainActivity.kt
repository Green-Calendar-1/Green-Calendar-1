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
                    val intentToBoard = Intent(this@MainActivity,BoardActivity::class.java)
                    startActivity(intentToBoard)

                }
                R.id.myDiaryMenu -> {
                    //val myDiaryFragment = MyDiaryFragment()
                    //supportFragmentManager.beginTransaction().replace(R.id.flFragment, myDiaryFragment).commit()
                    val intentToMyDiary = Intent(this@MainActivity,MyDiaryActivity::class.java)
                    startActivity(intentToMyDiary)
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