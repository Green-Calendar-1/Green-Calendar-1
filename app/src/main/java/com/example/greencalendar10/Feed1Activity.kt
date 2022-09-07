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
import com.example.greencalendar10.BoardWriteActivity
import com.example.greencalendar10.LoginActivity
import com.example.greencalendar10.MyApplication
import com.example.greencalendar10.R
import com.example.greencalendar10.databinding.ActivityFeed1Binding
import com.example.greencalendar10.model.Post
import com.example.greencalendar10.recycler.PostAdapter
import com.example.greencalendar10.util.myCheckPermission
import com.google.firebase.firestore.Query
// 9/5일 일지
// Feed1 만들었는데 서버에 데이터가 안올라감
// 댓글 기능도 수정해ㅑ야됨
// 일단 세영님 디자인에 적용은 했슴
// categoryfeed 폴더 삭제 -> 메니페스트에 적용안됨. 아마 이것 때문에 에러난듯
// 내일 1교시임 ㅅ발 일찍 자럭마 ㅂㅇ
class Feed1Activity : AppCompatActivity() {
    lateinit var binding: ActivityFeed1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFeed1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        myCheckPermission(this)
        binding.addFab.setOnClickListener {
            if(MyApplication.checkAuth()){
                startActivity(Intent(this, BoardWriteActivity::class.java))
            }else {
                Toast.makeText(this, "인증진행해주세요..", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onStart() {
        super.onStart()
        if(!MyApplication.checkAuth()){
            binding.logoutTextView.visibility= View.VISIBLE
            binding.mainRecyclerView.visibility= View.GONE
        }else {
            binding.logoutTextView.visibility= View.GONE
            binding.mainRecyclerView.visibility= View.VISIBLE
            makeRecyclerView()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        startActivity(Intent(this, LoginActivity::class.java))
        return super.onOptionsItemSelected(item)
    }

    private fun makeRecyclerView(){
        MyApplication.db.collection("feed1")
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {result ->
                val itemList = mutableListOf<Post>()
                for(document in result){
                    val item = document.toObject(Post::class.java)
                    item.docId=document.id
                    itemList.add(item)
                }
                binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
                binding.mainRecyclerView.adapter = PostAdapter(this,itemList)
            }
            .addOnFailureListener{exception ->
                Log.d("보드액티비티", "서버 데이터 획득 실패", exception)
                Toast.makeText(this, "서버 데이터 획득 실패", Toast.LENGTH_SHORT).show()
            }
    }
}