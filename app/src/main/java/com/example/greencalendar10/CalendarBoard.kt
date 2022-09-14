package com.example.greencalendar10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greencalendar10.databinding.ActivityBoardBinding
import com.example.greencalendar10.databinding.ActivityCalendarBoardBinding
import com.example.greencalendar10.model.Post
import com.example.greencalendar10.recycler.PostAdapter
import com.example.greencalendar10.util.myCheckPermission
import com.google.firebase.firestore.Query

class CalendarBoard : AppCompatActivity() {
    lateinit var binding: ActivityCalendarBoardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCalendarBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myCheckPermission(this)
        binding.boardRecyclerView.setOnClickListener {
            val commentListFragment = CommentListFragment()
            supportFragmentManager.beginTransaction().replace(R.id.flFragment, commentListFragment).commit()
        }

    }

    override fun onStart() {
        super.onStart()
        if(!MyApplication.checkAuth()){
            binding.logoutTextView.visibility= View.VISIBLE
            binding.boardRecyclerView.visibility= View.GONE
        }else {
            binding.logoutTextView.visibility= View.GONE
            binding.boardRecyclerView.visibility= View.VISIBLE
            makeRecyclerView()
        }
    }


    private fun makeRecyclerView(){
        val calendarDate = intent.getStringExtra("calendarDate")
        MyApplication.db.collection("posts")
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {result ->
                val itemList = mutableListOf<Post>()
                for(document in result){
                    val item = document.toObject(Post::class.java)
                    if(item.date.toString().slice(IntRange(0,6)) == calendarDate.toString().slice(IntRange(0,6))) {
                        item.docId = document.id
                        itemList.add(item)
                    }
                }
                binding.boardRecyclerView.layoutManager = LinearLayoutManager(this)
                binding.boardRecyclerView.adapter = PostAdapter(this,itemList)
            }
            .addOnFailureListener{exception ->
                Log.d("보드액티비티", "서버 데이터 획득 실패", exception)
                Toast.makeText(this, "서버 데이터 획득 실패", Toast.LENGTH_SHORT).show()
            }
    }
}