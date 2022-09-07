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
import com.example.greencalendar10.MyApplication
import com.example.greencalendar10.databinding.ActivityFeed1WriteBinding
import com.example.greencalendar10.util.dateToString
import java.io.File
import java.util.*

class Feed1WriteActivity : AppCompatActivity() {
    lateinit var binding: ActivityFeed1WriteBinding
    lateinit var filePath: String
    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFeed1WriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = getSharedPreferences("userPref", Context.MODE_PRIVATE)

        binding.addImageView.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*"
            )
            requestLauncher.launch(intent)
        }

        binding.addBtn.setOnClickListener {
            saveStore()
        }
    }
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
                .into(binding.addImageView) // addImageView로 전달

            // filePath를 가져오는 코드다.
            val cursor = contentResolver.query(it.data?.data as Uri,
                arrayOf<String>(MediaStore.Images.Media.DATA), null, null, null);
            cursor?.moveToFirst().let {
                filePath=cursor?.getString(0) as String
                Log.d("글쓰기 액티비티","filePath : $filePath")
            }
        }

    }

    private fun saveStore(){
        //add............................
        val data = mapOf(
            "nickname" to sharedPref.getString("nickname","없음"),
            "content" to binding.addEditView.text.toString(),
            "date" to dateToString(Date()),
        )
        MyApplication.db.collection("feed1")
            .add(data)
            .addOnSuccessListener {
                uploadImage(it.id)
                Log.d("아이디","${it.id}")
            }
            .addOnFailureListener{
                Log.d("ahn", "data save error", it)
            }
    }
    private fun uploadImage(docId: String){
        val storage = MyApplication.storage
        val storageRef = storage.reference
        val imgRef = storageRef.child("images/${docId}.jpg")

        val file = Uri.fromFile(File(filePath))
        Log.d("추가 화면에서 파일","$file")
        imgRef.putFile(file)
            .addOnSuccessListener {
                Toast.makeText(this, "저장 완료!!", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener{
                Log.d("ahn", "file save error", it)
            }
    }
}