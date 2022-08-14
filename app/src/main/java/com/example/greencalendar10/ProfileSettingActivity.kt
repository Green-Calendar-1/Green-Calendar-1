package com.example.greencalendar10

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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import java.io.File
import java.util.*

class ProfileSettingActivity : AppCompatActivity() {

    lateinit var binding:ActivityProfileSettingBinding
    lateinit var db:FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProfileSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var intent = Intent(this,MainActivity::class.java)
        db= FirebaseFirestore.getInstance()

        binding.settingBtn.setOnClickListener {
            saveStore()
            startActivity(intent)
        }

    }



    private fun saveStore(){

        val data = mapOf(
            "nickname" to binding.nicknameEt.text.toString(),
            "email" to binding.emailEt.text.toString(),
            "introduction" to binding.introductionEt.toString()
        )

        db.collection("users")
            .add(data)
            .addOnSuccessListener {
                Log.d("db 성공","db 성공")
            }
            .addOnFailureListener{
                Log.d("db 실패", "db 실패")
            }

    }

}