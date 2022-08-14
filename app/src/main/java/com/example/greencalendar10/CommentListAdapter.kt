package com.example.greencalendar10

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class CommentListAdapter(val context: Context, val commentList: ArrayList<Comment>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.comment_lv_item, null)

        val profileImg = view.findViewById<ImageView>(R.id.profileImg)
        val commentUserName = view.findViewById<TextView>(R.id.commentUserName)
        val commentContent = view.findViewById<TextView>(R.id.commentContent)
        val commentTime = view.findViewById<TextView>(R.id.commentTime)

        val comment = commentList[position]
        val resourceId = context.resources.getIdentifier(comment.profileImg, "drawable", context.packageName)
        profileImg.setImageResource(resourceId)
        commentUserName.text = comment.commentUserName
        commentContent.text = comment.commentContent
        commentTime.text = comment.commentTime

        return view
    }

    override fun getItem(position: Int): Any {
        return commentList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return commentList.size
    }


}