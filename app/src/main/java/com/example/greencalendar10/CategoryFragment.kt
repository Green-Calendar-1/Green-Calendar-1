package com.example.greencalendar10

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import com.example.greencalendar10.CategotyFeed.*
import com.example.greencalendar10.databinding.FragmentCategoryBinding
import com.example.greencalendar10.databinding.FragmentMyDiaryBinding

class CategoryFragment: Fragment() {
    private lateinit var binding: FragmentCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root



    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.categoryBtn1.setOnClickListener{
            activity?.let {
                val intentToFeed1 = Intent(context, Feed1Activity::class.java)
                startActivity(intentToFeed1)
            }
        }

        //게시물 쓰기 버튼 코드 추가
        binding.contentaddBtn.setOnClickListener {
            activity?.let{
                var activityPost = Intent(context, BoardActivity::class.java)
                startActivity(activityPost)
            }
        }
    }






}



