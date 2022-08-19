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
import com.example.greencalendar10.databinding.ActivityAddBinding
import com.example.greencalendar10.databinding.ActivityBoardBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

class BoardActivity : AppCompatActivity() {
    lateinit var binding: ActivityBoardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.addBtn.setOnClickListener {
            val intent = Intent(this,AddActivity::class.java)

            MyApplication.db.collection("users")
                .document(MyApplication.email.toString())
                .get()
                .addOnSuccessListener { document ->
                    val selectUser = document.toObject(User::class.java)
                    Log.d("ahn","name : ${selectUser?.nickname}")
                    binding.contentTv.text = selectUser?.nickname

                }
                .addOnFailureListener{
                    Log.d("ahn","글 불러오기 실패")
                }
            startActivity(intent)
        }

    }
}
