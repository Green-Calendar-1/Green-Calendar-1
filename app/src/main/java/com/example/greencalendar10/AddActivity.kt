package com.example.greencalendar10

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
import com.example.greencalendar10.MyApplication.Companion.auth
import com.example.greencalendar10.databinding.ActivityAddBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

// 서버에 사진, 글 저장하는 페이지
class AddActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddBinding
    lateinit var filePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveBtn.setOnClickListener {
            saveStore()
            val intent = Intent(this,BoardActivity::class.java)
            startActivity(intent)
        }
    }


    private fun saveStore(){
        val writing = mapOf(
            "email" to MyApplication.email,
            "content" to binding.contentEt.text.toString(),
            "date" to dateToString(Date())
        )


        MyApplication.db.collection("writings")
            .add(writing)
            .addOnSuccessListener {
                uploadImage(it.id)
            }
            .addOnFailureListener {
                Log.d("ahn","사진 업로드 실패")
            }

    }

    private fun uploadImage(docId: String){
        //add............................
        val storage = MyApplication.storage
        val storageRef = storage.reference
        val imgRef = storageRef.child("images/${docId}.jpg")

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
    fun dateToString(date: Date): String {
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(date)
    }

}