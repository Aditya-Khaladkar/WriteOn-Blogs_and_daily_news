package com.example.bajaj_task_1.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bajaj_task_1.R
import com.example.bajaj_task_1.model.BlogModel
import com.google.firebase.firestore.FirebaseFirestore

class MyBlogsAdapter(private val list: List<BlogModel>, private val context: Context) :
    RecyclerView.Adapter<MyBlogsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mblogListTitle: TextView = itemView.findViewById(R.id.mblogListTitle)
        val mblogListContent: TextView = itemView.findViewById(R.id.mblogListContent)
        val mDeleteBlog: ImageView = itemView.findViewById(R.id.mDeleteBlog)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_blog_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.mblogListTitle.text = item.title
        holder.mblogListContent.text = item.content

        holder.mDeleteBlog.setOnClickListener {
            Toast.makeText(context, "Swipe to delete blog", Toast.LENGTH_LONG).show()
        }
    }
}