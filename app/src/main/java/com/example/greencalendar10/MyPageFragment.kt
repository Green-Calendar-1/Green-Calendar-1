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

        /* 프로필 설정 창에서 저장된 닉네임을 가져와서 텍스트로 띄우기 (진행 중)
        프래그먼트에서 sharedPreference와 activity에서 쓰는 방법이 다름 알아봐야함...
        val sharedPref = activity.getSharedPreferences("savedNickname",Context.MODE_PRIVATE)
        val data = sharedPref.getString(savedNickname,savedNickname)
        */

        // 로그아웃 버튼 누를 경우
        // 구글만 했는데 네이버도 해야됨.
        // 로그아웃 시 다시 로그인 창으로 전환 필요
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















