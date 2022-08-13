package com.example.greencalendar10
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.greencalendar10.Board.BoardWriteActivity
import com.example.greencalendar10.catagoryChoose.*
import com.example.greencalendar10.databinding.FragmentCategoryBinding

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



        //글 추가 버튼이랑 글 작성하는 곳(activity_board_write) 바인딩
              binding.writeBtn.setOnClickListener {
            val intent = Intent(context, BoardWriteActivity::class.java )
            startActivity(intent)
        }


        //카테고리 선택 시 피드 연결
        binding.contentBtn1.setOnClickListener {
            val intent = Intent(context, FeedActivity::class.java)
            startActivity(intent)
        }

        binding.contentBtn2.setOnClickListener {
            val intent = Intent(context, FeedActivity2::class.java)
            startActivity(intent)
        }

        binding.contentBtn3.setOnClickListener {
            val intent = Intent(context, FeedActivity3::class.java)
            startActivity(intent)
        }

        binding.contentBtn4.setOnClickListener {
            val intent = Intent(context, FeedActivity4::class.java)
            startActivity(intent)
        }

        binding.contentBtn5.setOnClickListener {
            val intent = Intent(context, FeedActivity5::class.java)
            startActivity(intent)
        }

        binding.contentBtn6.setOnClickListener {
            val intent = Intent(context, FeedActivity6::class.java)
            startActivity(intent)
        }



        //하단바 페이지(프래그먼트) 바인딩
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