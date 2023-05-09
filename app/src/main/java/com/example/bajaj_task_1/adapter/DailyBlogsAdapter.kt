package com.example.bajaj_task_1.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bajaj_task_1.R
import com.example.bajaj_task_1.model.DailyBlogModel

class DailyBlogsAdapter(var blogsList: List<DailyBlogModel>) :
    RecyclerView.Adapter<DailyBlogsAdapter.DailyBlogViewHolder>() {
    class DailyBlogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_newsRequestList = itemView.findViewById<TextView>(R.id.txt_newsRequestList)
        val img_newsRequestList = itemView.findViewById<ImageView>(R.id.img_newsRequestList)
        val card_Layout = itemView.findViewById<CardView>(R.id.card_Layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyBlogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.daily_blog_list, parent,
            false
        )
        return DailyBlogViewHolder(view)
    }

    override fun onBindViewHolder(holder: DailyBlogViewHolder, position: Int) {
        val item = blogsList[position]
        holder.txt_newsRequestList.text = item.title
        Glide.with(holder.img_newsRequestList.context)
            .load(item.imageUrl)
            .into(holder.img_newsRequestList)

        holder.card_Layout.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("imageUrl", item.imageUrl)
            bundle.putString("content", item.content)
            bundle.putString("author", item.author)
            bundle.putString("url", item.url)
            Navigation.findNavController(it)
                .navigate(R.id.action_nav_dailyBlog_to_dailyBlogDetails, bundle)
        }
    }

    override fun getItemCount(): Int {
        return blogsList.size
    }

    fun updateNewsList(newBlogsList: List<DailyBlogModel>) {
        blogsList = newBlogsList
        notifyDataSetChanged()
    }
}