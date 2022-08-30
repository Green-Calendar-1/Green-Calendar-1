package com.example.greencalendar10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greencalendar10.databinding.ActivityNotificationListBinding
import com.example.greencalendar10.model.Comment
import com.example.greencalendar10.recycler.NotificationListAdapter
import com.google.firebase.firestore.Query

class NotificationList : AppCompatActivity() {
    lateinit var binding: ActivityNotificationListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotificationListBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()
        makeRecyclerView()
    }

    private fun makeRecyclerView(){
        // 서버에서 데이터 받아서 리사이클러뷰에 적용
        MyApplication.db.collection("comments")
            .orderBy("commentTime", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                val notificationList = mutableListOf<Comment>() // Notification이 아닌 Comment 활용
                for(document in result){
                    val item = document.toObject(Comment::class.java)
                    item.docId=document.id
                    notificationList.add(item)
                    Log.d("알림","Comment 서버로 부터 데이터 받기 성공")
                }
                // 이게 리사이클러뷰 적용
                binding.notificationRecyclerView.layoutManager = LinearLayoutManager(this)
                binding.notificationRecyclerView.adapter = NotificationListAdapter(this, notificationList)
            }
            .addOnFailureListener{exception ->
                Log.d("알림", "서버에서 데이터 받기 실패", exception)
                Toast.makeText(this, "서버 데이터 획득 실패", Toast.LENGTH_SHORT).show()
            }
    }
}