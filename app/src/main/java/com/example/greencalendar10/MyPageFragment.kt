package com.example.greencalendar10

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.greencalendar10.databinding.FragmentMyPageBinding
import com.example.greencalendar10.model.User

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

        val imgRef = MyApplication.storage.reference.child("profiles/${MyApplication.email}.jpg")
        imgRef.downloadUrl.addOnCompleteListener{ task ->
            if(task.isSuccessful){
                Glide.with(this)
                    .load(task.result)
                    .into(binding.profileIv)
            }
        }


        // 속도 향상을 위해 서버 말고 sharePreference로 바꾸자 너무 오래 걸린다.
        MyApplication.db.collection("users")
            .document("${MyApplication.email}")
            .get()
            .addOnSuccessListener { document ->
                val selectUser = document.toObject(User::class.java)
                binding.nicknameTv.text = selectUser?.nickname
                binding.emailTv.text = selectUser?.email
                binding.introductionTv.text = selectUser?.introduction
            }
            .addOnFailureListener{
                Log.d("마이페이지","글 불러오기 실패")
            }


        // 로그아웃 버튼 누를 경우

        val intentToLoginActivity = Intent(activity,LoginActivity::class.java)
        val intentToProfileSettingActivity = Intent(activity,ProfileSettingActivity::class.java)
        // 구글만 했는데 네이버도 해야됨.
        // 로그아웃 시 다시 로그인 창으로 전환 필요
        Log.d("마이페이지", "로그아웃 버튼 전")
        binding.logoutBtn.setOnClickListener {
            MyApplication.auth.signOut()
            MyApplication.email = null
            Toast.makeText(
                context,
                "로그아웃 성공",
                Toast.LENGTH_SHORT
            ).show()
            Log.d("마이페이지", "로그아웃")
            startActivity(intentToLoginActivity)
        }
        binding.editBtn.setOnClickListener{
            startActivity(intentToProfileSettingActivity)
        }



    }

}

