package com.example.greencalendar10.recycler

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.greencalendar10.MyApplication
import com.example.greencalendar10.databinding.CommentLvItemBinding
import com.example.greencalendar10.model.Comment

class CommentViewHolder(val binding: CommentLvItemBinding) : RecyclerView.ViewHolder(binding.root)

class CommentListAdapter(val context: Context, val commentList: MutableList<Comment>) : RecyclerView.Adapter<CommentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CommentViewHolder(CommentLvItemBinding.inflate(layoutInflater))
    }

    override fun getItemCount(): Int {
        return commentList.size
    }
    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val data = commentList.get(position)

        holder.binding.run {
            commentUserNameTv.text=data.commentUserName
            commentContentTv.text=data.commentContent
            commentTimeTv.text=data.commentTime
        }

        val imgRef = MyApplication.storage.reference.child("comments/${data.docId}.jpg")
        Log.d("데이터 doc","${data.docId}")
        imgRef.downloadUrl.addOnCompleteListener{ task ->
            if(task.isSuccessful){
                Glide.with(context)
                    .load(task.result)
                    .into(holder.binding.profileImgIv)
            }
        }
    }


}