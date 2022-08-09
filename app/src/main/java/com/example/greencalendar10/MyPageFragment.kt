package com.example.greencalendar10

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
import com.google.firebase.ktx.Firebase

class MyPageFragment: Fragment(R.layout.fragment_my_page) {
    lateinit var auth: FirebaseAuth

    // 프래그먼트에서 바인딩
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
        auth= Firebase.auth

        // 로그아웃 버튼 누를 경우
        Log.d("로그아웃 버튼 전","로그아웃 버튼 전")
        binding.logoutBtn.setOnClickListener {
            auth.signOut()
            Toast.makeText(
                context,
                "로그아웃 성공",
                Toast.LENGTH_SHORT
            ).show()
            Log.d("로그아웃","로그아웃")

        }
    }

}