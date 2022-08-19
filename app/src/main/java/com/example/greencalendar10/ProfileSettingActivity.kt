package com.example.greencalendar10

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
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
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.storage.StorageReference
import java.io.File
import java.util.*

class ProfileSettingActivity : AppCompatActivity() {

    lateinit var binding:ActivityProfileSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProfileSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var intent = Intent(this,MainActivity::class.java)

        binding.settingBtn.setOnClickListener {
            saveStore()
            startActivity(intent)
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
            }
            .addOnFailureListener{
                Log.d("db 실패", "db 실패")
            }
    }
}