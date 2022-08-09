package com.example.greencalendar10

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.greencalendar10.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {


    private lateinit var binding : FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("HomeFragment", "onCreateView")

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)

        binding.categoryTap.setOnClickListener {

            Log.d("HomeFragment", "onCreateView")
            it.findNavController().navigate(R.id.action_homeFragment_to_categoryFragment)
        }

        binding.diaryTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_myDiaryFragment)
        }
        binding.mypageTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_homeFragment_to_myPageFragment)
        }

        return binding.root
    }


    }

