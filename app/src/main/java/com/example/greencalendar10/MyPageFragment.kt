package com.example.greencalendar10


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.greencalendar10.databinding.FragmentMyPageBinding

class MyPageFragment: Fragment() {


    private lateinit var binding : FragmentMyPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("MyPageFragment", "onCreateView")

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_page,container,false)

        binding.categoryTap.setOnClickListener {

            Log.d("MyPageFragment", "onCreateView")
            it.findNavController().navigate(R.id.action_myPageFragment_to_categoryFragment)
        }

        binding.homeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_myPageFragment_to_homeFragment)
        }
        binding.diaryTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_myPageFragment_to_myDiaryFragment)
        }

        return binding.root
    }


}
