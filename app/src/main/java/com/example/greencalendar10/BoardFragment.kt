package com.example.greencalendar10

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greencalendar10.databinding.ActivityBoardBinding
import com.example.greencalendar10.databinding.FragmentBoardBinding
import com.example.greencalendar10.model.Post
import com.example.greencalendar10.recycler.PostAdapter
import com.example.greencalendar10.util.myCheckPermission
import com.google.firebase.firestore.Query

class BoardFragment : Fragment() {
    //액티비티를 프래그먼트로 변환하는데 오류생김 일단 액티비티로 끌고감
    lateinit var binding: FragmentBoardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        makeRecyclerView()
        binding = FragmentBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //myCheckPermission(requireActivity() as AppCompatActivity)
        binding.addFab.setOnClickListener {
            if(MyApplication.checkAuth()){
                //startActivity(Intent(this, BoardWriteActivity::class.java))
                val boardFragment = BoardFragment()
                childFragmentManager.beginTransaction().replace(R.id.flFragment, boardFragment).commit()
            }else {
                Toast.makeText(activity, "인증진행해주세요..", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun makeRecyclerView(){
        MyApplication.db.collection("posts")
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {result ->
                val itemList = mutableListOf<Post>()
                for(document in result){
                    val item = document.toObject(Post::class.java)
                    item.docId=document.id
                    itemList.add(item)
                }
                binding.mainRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
                binding.mainRecyclerView.adapter = PostAdapter(requireActivity(),itemList)
            }
            .addOnFailureListener{exception ->
                Log.d("보드액티비티", "서버 데이터 획득 실패", exception)
                Toast.makeText(activity, "서버 데이터 획득 실패", Toast.LENGTH_SHORT).show()
            }
    }

}