package com.example.greencalendar10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class CommentList : AppCompatActivity() {

    // test ..
    var commentList = arrayListOf<Comment>(
        Comment("윤현조","안녕하세요","1시간 전", "profile_temp"),
        Comment("박세영","안녕하세요","2시간 전", "profile_temp"),
        Comment("안영준","안녕하세요","3시간 전", "profile_temp"),
        Comment("김향은","안녕하세요","5분 전", "profile_temp")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_list)

        val commentAdaper = CommentListAdapter(this, commentList)

        val commentListView: ListView = findViewById(R.id.commentListView)
        commentListView.adapter = commentAdaper
    }
}