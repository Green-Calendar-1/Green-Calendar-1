package com.example.greencalendar10
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.greencalendar10.databinding.FragmentCategoryBinding
import com.example.greencalendar10.databinding.FragmentMyPageBinding

class CategoryFragment: Fragment() {


    private lateinit var binding : FragmentCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("CategoryFragment","onCreateView")

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_category,container,false)

        binding.homeTap.setOnClickListener {

            Log.d("CategoryFragment", "onCreateView")
            it.findNavController().navigate(R.id.action_categoryFragment_to_homeFragment)
        }

        binding.mypageTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_categoryFragment_to_myPageFragment)
        }
        binding.diaryTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_categoryFragment_to_myDiaryFragment)
        }

        return binding.root
    }


}