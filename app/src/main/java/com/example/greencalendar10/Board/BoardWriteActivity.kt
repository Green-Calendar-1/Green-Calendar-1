package com.example.greencalendar10.Board

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.greencalendar10.R
import com.example.greencalendar10.databinding.ActivityBoardWriteBinding

class BoardWriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBoardWriteBinding

    //boardwriteactivity 가 택에 담김
    private val TAG = BoardWriteActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_write)

        //글쓰기 페이지(board write)에서 제목,쓴글, 이미지 받아오기
        binding.addBtn.setOnClickListener {

            val title = binding.titleArea.text.toString() //textArea에 있는 문자를 받아온다는 뜻
            val content = binding.contentArea.text.toString()

            Log.d(TAG, title)
            Log.d(TAG, content)
        //확인
        }
    }

}