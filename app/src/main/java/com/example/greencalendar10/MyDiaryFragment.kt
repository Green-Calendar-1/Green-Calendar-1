package com.example.greencalendar10

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.greencalendar10.databinding.FragmentMyDiaryBinding

class MyDiaryFragment: Fragment(){
    private lateinit var binding: FragmentMyDiaryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // 댓글창 바인딩
        binding = FragmentMyDiaryBinding.inflate(inflater, container, false)
        val mActivity = activity as MainActivity
        val btnCommentListFragment =  binding.commentListFragBtn

        btnCommentListFragment.setOnClickListener{
            mActivity.changeFragment(1)
        }
        return binding.root
    }
}
