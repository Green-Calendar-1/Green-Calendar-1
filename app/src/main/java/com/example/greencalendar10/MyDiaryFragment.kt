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
import com.example.greencalendar10.databinding.FragmentMyDiaryBinding

class MyDiaryFragment: Fragment() {


    private lateinit var binding : FragmentMyDiaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("MyDiaryFragment", "onCreateView")

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_diary,container,false)

        binding.categoryTap.setOnClickListener {

            Log.d("MyDiaryFragment", "onCreateView")
            it.findNavController().navigate(R.id.action_myDiaryFragment_to_categoryFragment)
        }

        binding.homeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_myDiaryFragment_to_homeFragment)
        }
        binding.mypageTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_myDiaryFragment_to_myPageFragment)
        }

        return binding.root
    }


}
