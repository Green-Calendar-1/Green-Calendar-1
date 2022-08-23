package com.example.greencalendar10

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import com.example.greencalendar10.CategotyFeed.*
import com.example.greencalendar10.databinding.FragmentCategoryBinding

class CategoryFragment: Fragment() {

    private lateinit var binding : FragmentCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //binding.DataBindingUtil.inflate(inflater,R.layout.fragment_category,container,false)

        /*binding.addBtn.setOnClickListener {
            val intent = Intent(context, BoardWriteActivity::class.java) //boardwrite로 이동
            startActivity(intent)
        }*/

        //피드(1~버튼 개수만큼)
        binding.categoryBtn1.setOnClickListener {
            val intent = Intent(context, Feed1Activity::class.java) //피드1로 이동
            startActivity(intent)
        }

        binding.categoryBtn2.setOnClickListener {
            val intent = Intent(context, Feed2Activity::class.java)
            startActivity(intent)
        }

        binding.categoryBtn3.setOnClickListener {
            val intent = Intent(context, Feed3Activity::class.java)
            startActivity(intent)
        }

        binding.categoryBtn4.setOnClickListener {
            val intent = Intent(context, Feed4Activity::class.java)
            startActivity(intent)
        }
        binding.categoryBtn5.setOnClickListener {
            val intent = Intent(context, Feed5Activity::class.java)
            startActivity(intent)
        }

        binding.categoryBtn6.setOnClickListener {
            val intent = Intent(context, Feed6Activity::class.java)
            startActivity(intent)
        }



        return binding.root
    }
}

