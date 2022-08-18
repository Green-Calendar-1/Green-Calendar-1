package com.example.greencalendar10

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import com.example.greencalendar10.databinding.ActivityBoardWriteBinding

class BoardWriteActivity : AppCompatActivity(){

    private lateinit var binding: ActivityBoardWriteBinding

    private val TAG = BoardWriteActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?){

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_board_write)

        binding.writeBtn.setOnClickListener {

            //title,content 쓰는 공간 바인딩
            val title= binding.titleArea.text.toString()
            val content = binding.contentArea.text.toString()

            //boardwrite 액티비티가 태그에 담겨서 찍힘(데이터베이스에)
            Log.d(TAG, title)
            Log.d(TAG, content)

            //여기서 부터 파이어베이스랑 연결 해야함 !!

        }
    }
}