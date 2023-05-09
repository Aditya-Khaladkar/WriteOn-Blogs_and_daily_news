package com.example.bajaj_task_1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bajaj_task_1.R
import com.example.bajaj_task_1.model.AuthorsModel

class AuthorAdapter(private val list: List<AuthorsModel>) : RecyclerView.Adapter<AuthorAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val authorsListTitle: TextView = itemView.findViewById(R.id.authorsListTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.treding_author_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.authorsListTitle.text = item.author
    }
}