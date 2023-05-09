package com.example.bajaj_task_1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bajaj_task_1.R
import com.example.bajaj_task_1.model.BlogModel

class BlogAdapter(private val list: List<BlogModel>) : RecyclerView.Adapter<BlogAdapter.BlogViewHolder>() {
    class BlogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val blogListTitle: TextView = itemView.findViewById(R.id.blogListTitle)
        val blogListContent: TextView = itemView.findViewById(R.id.blogListContent)
        val blogListAuthor: TextView = itemView.findViewById(R.id.blogListAuthor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.blog_list, parent, false)
        return BlogViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        val item = list[position]
        holder.blogListTitle.text = item.title
        holder.blogListContent.text = item.content
        holder.blogListAuthor.text = "- ${item.author}"
    }

}