package com.example.greencalendar10

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.greencalendar10.CategotyFeed.*
import com.example.greencalendar10.databinding.FragmentCategoryBinding

class CategoryFragment: Fragment() {


    private lateinit var binding: FragmentCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // 댓글창 바인딩
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        val mActivity = activity as MainActivity
        val activityBoardWriteActivity =  binding.contentaddBtn

        activityBoardWriteActivity.setOnClickListener{
            mActivity.changeFragment(1)
        }

        return binding.root
    }

}

