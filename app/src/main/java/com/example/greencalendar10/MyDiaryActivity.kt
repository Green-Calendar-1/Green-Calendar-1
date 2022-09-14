package com.example.greencalendar10

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greencalendar10.databinding.ActivityBoardBinding
import com.example.greencalendar10.databinding.ActivityMyDiaryBinding
import com.example.greencalendar10.model.Post
import com.example.greencalendar10.recycler.PostAdapter
import com.example.greencalendar10.util.myCheckPermission
import com.google.firebase.firestore.Query

class MyDiaryActivity : AppCompatActivity() {
    lateinit var binding: ActivityMyDiaryBinding
    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myCheckPermission(this)


    }

    override fun onStart() {
        super.onStart()
        sharedPref = getSharedPreferences("userPref", Context.MODE_PRIVATE)
        if(MyApplication.checkAuth()){
            binding.logoutTextView.visibility= View.GONE
            binding.boardRecyclerView.visibility= View.VISIBLE
            makeRecyclerView()
        }
    }



    private fun makeRecyclerView(){
        MyApplication.db.collection("posts")
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {result ->
                val itemList = mutableListOf<Post>()
                for(document in result){
                    val item = document.toObject(Post::class.java)
                    if(sharedPref.getString("nickname","") == item.nickname) {
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