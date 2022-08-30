package com.example.greencalendar10.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.greencalendar10.MyApplication
import com.example.greencalendar10.model.Notification
import com.example.greencalendar10.databinding.NotificationLvItemBinding
import com.example.greencalendar10.model.Comment

class NotificationViewHolder(val binding: NotificationLvItemBinding) : RecyclerView.ViewHolder(binding.root)

class NotificationListAdapter(val context: Context, val notificationList: MutableList<Comment>) : RecyclerView.Adapter<NotificationViewHolder>() {
    // 댓글의 nickname과 시간에 따라 notification의 내용이 바뀌므로 model의 Comment를 사용. Notification 없어도 됨
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NotificationViewHolder(NotificationLvItemBinding.inflate(layoutInflater))
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val data = notificationList.get(position)

        holder.binding.run {
            notificationUserNameTv.text = data.commentUserName
            notificationContentTv.text= " 님이 댓글을 남겼습니다."
            notificationTimeTv.text=data.commentTime
        }

        // 이미지 가져오기
        val imgRef = MyApplication.storage.reference.child("comments/${data.docId}.jpg")
        imgRef.downloadUrl.addOnCompleteListener{ task ->
            if(task.isSuccessful){
                Glide.with(context)
                    .load(task.result)
                    .into(holder.binding.profileImgIv)
            }
        }
    }
}