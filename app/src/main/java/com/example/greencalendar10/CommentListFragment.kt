package com.example.greencalendar10

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greencalendar10.databinding.FragmentCommentListBinding
import com.example.greencalendar10.model.Comment
import com.example.greencalendar10.recycler.CommentListAdapter
import com.example.greencalendar10.util.dateToString
import java.io.File
import java.util.*

class CommentListFragment: Fragment() {

    lateinit var binding: FragmentCommentListBinding
    lateinit var sharedPref : SharedPreferences


    override fun onStart(){
        super.onStart()
        makeRecyclerView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommentListBinding.inflate(inflater, container, false)
        sharedPref = requireActivity().getSharedPreferences("userPref", Context.MODE_PRIVATE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.commentListView

        val commentAdapter = CommentListAdapter(requireContext(), commentList)

        val commentListView = binding.commentListView
        commentListView.adapter = commentAdapter*/
        binding.commentInputBtn.setOnClickListener {
            saveStore()
            makeRecyclerView()
        }

    }

    private fun saveStore(){
        // 닉네임은 sharedPreference, 댓글 내용은 입력값, 시간은 MyUtil 활용. 날짜 수정하고 싶으면 MyUtil 창으로
        val comment = mapOf(
            "commentUserName" to sharedPref.getString("nickname",""),
            "commentContent" to binding.commentInputEt.text.toString(),
            "commentTime" to dateToString(Date())
        )
        // 댓글도 서버에 따로 보관하자
        MyApplication.db.collection("comments")
            // add 이기 때문에 임의의 문서값으로 데이터 저장
            .add(comment)
            .addOnSuccessListener {
                uploadImage(it.id)
                Log.d("나의 일기","서버 업로드 성공")
            }
            .addOnFailureListener{
                Log.d("나의 일기", "서버 업로드 실패")
            }
    }

    private fun uploadImage(docId: String){
        val storage = MyApplication.storage
        val storageRef = storage.reference

        // 임의로 저장된 댓글의 문서값과 이미지의 저장값을 같게 해서 연관성을 준다.
        val imgRef = storageRef.child("comments/${docId}.jpg")

        // 프로필 설정창에서는 갤러리에서 이미지를 선택해서 파일 경로를 구했는데 여기선 그럴 수가 없네...
        // 1. intent로 넘겨주기... (x) -> 프로필 설정창이 처음 앱을 실행할 때만 실행되니까 두 번째 실행 시에는 null 값이라 안되네
        // 2. MyApplication에 전역 변수로 선언해서 사용하기... (x) -> 1과 같은 이유로 null값 이더라... 안됨
        // 3. 서버에서 받기... (안해봄)
        // 4. sharedPreference 사용하기 (o) -> 성공

        // sharedPreference의 filePath (프로필 설정창에서 가져옴)
        val filePath = sharedPref.getString("filePath","")

        val file = Uri.fromFile(File(filePath))
        imgRef.putFile(file)
            .addOnFailureListener{
                Log.d("나의 일기","comments에 파일 업로드 실패")
            }
            .addOnSuccessListener {
                Log.d("나의 일기","comments에 파일 업로드 성공")
            }
    }

    private fun makeRecyclerView(){
        MyApplication.db.collection("comments")
            .orderBy("commentTime")
            .get()
            .addOnSuccessListener {result ->
                val commentList = mutableListOf<Comment>()
                for(document in result){
                    val item = document.toObject(Comment::class.java)
                    item.docId=document.id
                    Log.d("그림 id","${item.docId}")
                    commentList.add(item)
                }
                binding.commentRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.commentRecyclerView.adapter = CommentListAdapter(requireContext(), commentList)
            }
            .addOnFailureListener{exception ->
                Log.d("ahn", "error.. getting document..", exception)
                Toast.makeText(requireActivity(), "서버 데이터 획득 실패", Toast.LENGTH_SHORT).show()
            }
    }
}

