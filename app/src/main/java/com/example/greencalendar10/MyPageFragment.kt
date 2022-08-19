package com.example.greencalendar10

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.greencalendar10.databinding.FragmentMyPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.ktx.Firebase

class MyPageFragment: Fragment(R.layout.fragment_my_page) {
    lateinit var binding: FragmentMyPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentMyPageBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MyApplication.db.collection("users")
            .document("{$MyApplication.email}")
            .get()
            .addOnSuccessListener { document ->
                val selectUser = document.toObject(User::class.java)
                binding.nicknameTv.text = selectUser?.nickname
                binding.emailTv.text = selectUser?.email
                binding.introductionTv.text = selectUser?.introduction

            }
            .addOnFailureListener{
                Log.d("ahn","글 불러오기 실패")
            }

        // 로그아웃 버튼 누를 경우


        // 구글만 했는데 네이버도 해야됨.
        // 로그아웃 시 다시 로그인 창으로 전환 필요
        Log.d("로그아웃 버튼 전", "로그아웃 버튼 전")
        binding.logoutBtn.setOnClickListener {
            MyApplication.auth.signOut()
            MyApplication.email = null
            Toast.makeText(
                context,
                "로그아웃 성공",
                Toast.LENGTH_SHORT
            ).show()
            Log.d("로그아웃", "로그아웃")
        }


    }

}

