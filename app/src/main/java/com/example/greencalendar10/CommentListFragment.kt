package com.example.greencalendar10

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.navigation.fragment.findNavController
import com.example.greencalendar10.databinding.FragmentCommentListBinding

class CommentListFragment: Fragment() {

    private var _binding: FragmentCommentListBinding? = null
    private val binding get() = _binding!!

    // test
    var commentList = arrayListOf<Comment>(
        Comment("윤현조","안녕하세요","1시간 전", "profile_temp"),
        Comment("박세영","안녕하세요","2시간 전", "profile_temp"),
        Comment("안영준","안녕하세요","3시간 전", "profile_temp"),
        Comment("김향은","안녕하세요","5분 전", "profile_temp"),
        Comment("윤현조","안녕하세요","1시간 전", "profile_temp"),
        Comment("박세영","안녕하세요","2시간 전", "profile_temp"),
        Comment("안영준","안녕하세요","3시간 전", "profile_temp"),
        Comment("김향은","안녕하세요","5분 전", "profile_temp")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.commentListView

        val commentAdapter = CommentListAdapter(requireContext(), commentList)

        val commentListView = binding.commentListView
        commentListView.adapter = commentAdapter
    }
}

