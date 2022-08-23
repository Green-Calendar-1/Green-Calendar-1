package com.example.greencalendar10

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.greencalendar10.databinding.ActivityProfileSettingBinding
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.storage.StorageReference
import io.grpc.InternalChannelz.id
import java.io.File
import java.util.*

class ProfileSettingActivity : AppCompatActivity() {

    lateinit var binding:ActivityProfileSettingBinding
    lateinit var filePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProfileSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 갤러리 요청 런치
        // 갤러리 앱에서 사진 데이터 가져오기
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
                    Log.d("ahn filePath","$filePath")
                }
            }
        }

        binding.profileIv.setOnClickListener{
            // 갤러리 앱의 목록으로 화면 전환
            val intentToGallery = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intentToGallery.type="image/*"
            requestLauncher.launch(intentToGallery)
        }


        val intentToMainActivity = Intent(this,MainActivity::class.java)

        binding.settingBtn.setOnClickListener {
            saveStore()
            startActivity(intentToMainActivity)
        }

    }
    private fun saveStore(){

        val user = mapOf(
            "nickname" to binding.nicknameEt.text.toString(),
            "email" to MyApplication.email,
            "introduction" to binding.introductionEt.text.toString()
        )
        MyApplication.db.collection("users")
            .document("${MyApplication.email}")
            .set(user)
            .addOnSuccessListener {
                Log.d("db 불러오기 성공","db 불러오기 성공")
                uploadImage("${MyApplication.email}")
            }
            .addOnFailureListener{
                Log.d("db 실패", "db 실패")
            }
    }

    private fun uploadImage(docId: String){
        //add............................
        val storage = MyApplication.storage
        val storageRef = storage.reference
        val imgRef = storageRef.child("profiles/${docId}.jpg")

        val file = Uri.fromFile(File(filePath))
        imgRef.putFile(file)
            .addOnSuccessListener {
                Toast.makeText(this, "save ok..", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener{
                Log.d("ahn", "file save error", it)
            }
    }

}