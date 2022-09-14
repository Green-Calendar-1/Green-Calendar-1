package com.example.greencalendar10

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.greencalendar10.databinding.ActivityBoardWriteBinding
import com.example.greencalendar10.util.dateToString
import java.io.File
import java.util.*

class BoardWriteActivity : AppCompatActivity(){
    lateinit var binding: ActivityBoardWriteBinding
    lateinit var filePath: String
    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBoardWriteBinding.inflate(layoutInflater)
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

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId === R.id.menu_add_gallery){
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*"
            )
            requestLauncher.launch(intent)
        }else if(item.itemId === R.id.menu_add_save){
            if(binding.addImageView.drawable !== null && binding.addEditView.text.isNotEmpty()){
                //store 에 먼저 데이터를 저장후 document id 값으로 업로드 파일 이름 지정
                saveStore()
            }else {
                Toast.makeText(this, "데이터가 모두 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
            }

        }
        return super.onOptionsItemSelected(item)
    }

     */
     */
    private fun saveStore(){
        //add............................
        val data = mapOf(
            "nickname" to sharedPref.getString("nickname","없음"),
            "content" to binding.addEditView.text.toString(),
            "date" to dateToString(Date()),
        )
        MyApplication.db.collection("posts")
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