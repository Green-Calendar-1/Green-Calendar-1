package com.example.greencalendar10

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.greencalendar10.databinding.ActivityProfileSettingBinding

class ProfileSettingActivity : AppCompatActivity() {

    lateinit var binding:ActivityProfileSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var intent = Intent(this,MainActivity::class.java)
        val sharedPref = getPreferences(Context.MODE_PRIVATE)

        // 저장 버튼 눌렀을 때 sharedPreference를 통해 닉네임 내부 저장소에 저장
        binding.settingBtn.setOnClickListener {
            val nickname:String = binding.nicknameEt.text.toString()

            sharedPref.edit().run{
                putString("nickname1",nickname)
                commit()
            }
            val savedNickname = sharedPref.getString("savedNickname",nickname)
            // 확인을 위해 닉네임 toast 해봄
            Toast.makeText(
                baseContext,
                "닉네임: $savedNickname",
                Toast.LENGTH_SHORT
            ).show()
            // 프로필 설정 창에서 메인 액티비티로 이동
            // 조건문 활용해서 초기 설정 시에만 프로필 설정창으로 이동하고 다른 경우 메인 액티비티로 바로 이동하게 설정해야함. 조건 상태를 아직 모름
            startActivity(intent)
        }

    }
}