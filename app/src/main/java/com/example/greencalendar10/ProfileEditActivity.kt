package com.example.greencalendar10

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.greencalendar10.databinding.ActivityProfileEditBinding
import com.example.greencalendar10.databinding.ActivityProfileSettingBinding
import java.io.File

class ProfileEditActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileEditBinding
    lateinit var filePath: String
    lateinit var sharedPref: SharedPreferences
    lateinit var file: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 갤러리 요청 런치
        // 갤러리 앱에서 사진 데이터 가져와 filePath에 경로 저장
        val requestLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
        {
            if(it.resultCode === android.app.Activity.RESULT_OK){
                Glide
                    .with(getApplicationContext())
                    .load(it.data?.data)    // 리소스 전달
                    .apply(RequestOptions().override(250, 200))
                    .centerCrop()
                    .into(binding.profileIv) // addImageView로 전달

                // filePath를 가져오는 코드다.
                val cursor = contentResolver.query(it.data?.data as Uri,
                    arrayOf<String>(MediaStore.Images.Media.DATA), null, null, null);
                cursor?.moveToFirst().let {
                    filePath=cursor?.getString(0) as String
                    Log.d("프로필 설정","filePath : $filePath")
                }
            }
        }

        binding.profileIv.setOnClickListener{
            // 갤러리 앱의 목록으로 화면 전환
            val intentToGallery = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intentToGallery.type="image/*"
            requestLauncher.launch(intentToGallery)
        }

        val intentToMainActivity = Intent(this,MainActivity::class.java)

        binding.settingBtn.setOnClickListener {
            saveStore()
            // startActivity(intentToMainActivity)
            // 종료 후 프로필 수정 전, 즉 마이페이지로 돌아가게 하고 싶어서 수정하였음
            finish()
        }

    }
    private fun saveStore(){
        // 프로필 설정창에서 닉네임, 한 줄 소개 값을 받는다.
        val user = mapOf(
            "nickname" to binding.nicknameEt.text.toString(),
            "email" to MyApplication.email,
            "introduction" to binding.introductionEt.text.toString()
        )
        // 서버에 저장
        MyApplication.db.collection("users")
            // 유저 정보라 이메일 값으로 유저정보 저장
            .document("${MyApplication.email}")
            .set(user)
            .addOnSuccessListener {
                Log.d("프로필 설정","서버에 유저 정보 저장 성공")
                // 이미지도 이메일값으로 저장
                uploadImage("${MyApplication.email}")

                // 닉네임, 한 줄 소개, 이미지 filePath 값을 sharedPreference로 핸드폰 내부에 저장함
                // -> 글쓰기 창, 댓글창, 마이페이지에서 활용
                sharedPref = getSharedPreferences("userPref", Context.MODE_PRIVATE)
                sharedPref.edit().run{
                    putString("nickname",binding.nicknameEt.text.toString())
                    putString("introduction",binding.introductionEt.text.toString())
                    putString("filePath",filePath)
                    commit()
                }
            }
            .addOnFailureListener{
                Log.d("프로필 설정", "서버에 유저 정보 저장 실패")
            }
    }
    // 프로필 이미지 업로드
    private fun uploadImage(docId: String){ // 유저 정보는 docId 값을 이메일 값으로 저장했다.
        val storage = MyApplication.storage
        val storageRef = storage.reference
        val imgRef = storageRef.child("profiles/${docId}.jpg")

        file = Uri.fromFile(File(filePath))

        Log.d("프로필 설정","파일 값: $file")
        imgRef.putFile(file)
            .addOnSuccessListener {
                Toast.makeText(this, "프로필 저장 완료!!", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener{
                Log.d("프로필 설정", "프로필 사진 저장 실패")
            }
    }
}