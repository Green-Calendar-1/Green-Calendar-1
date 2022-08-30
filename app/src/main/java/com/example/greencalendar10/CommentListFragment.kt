package com.example.greencalendar10

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.greencalendar10.databinding.FragmentCommentListBinding
import com.example.greencalendar10.model.Comment
import com.example.greencalendar10.recycler.CommentListAdapter
import java.io.File

class CommentListFragment: Fragment() {

    private var _binding: FragmentCommentListBinding? = null
    private val binding get() = _binding!!

    // test
   /* var commentList = arrayListOf<Comment>(
        Comment("윤현조","안녕하세요","1시간 전", "profile_temp"),
        Comment("박세영","안녕하세요","2시간 전", "profile_temp"),
        Comment("안영준","안녕하세요","3시간 전", "profile_temp"),
        Comment("김향은","안녕하세요","5분 전", "profile_temp"),
        Comment("윤현조","안녕하세요","1시간 전", "profile_temp"),
        Comment("박세영","안녕하세요","2시간 전", "profile_temp"),
        Comment("안영준","안녕하세요","3시간 전", "profile_temp"),
        Comment("김향은","안녕하세요","5분 전", "profile_temp")
    )*/
    override fun onStart(){
        super.onStart()
        makeRecyclerView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /*super.onViewCreated(view, savedInstanceState)

        binding.commentListView

        val commentAdapter = CommentListAdapter(requireContext(), commentList)

        val commentListView = binding.commentListView
        commentListView.adapter = commentAdapter*/

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

