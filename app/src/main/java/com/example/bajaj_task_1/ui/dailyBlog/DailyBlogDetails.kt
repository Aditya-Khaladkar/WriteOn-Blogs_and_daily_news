package com.example.bajaj_task_1.ui.dailyBlog

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.bajaj_task_1.databinding.FragmentDailyBlogDetailsBinding

class DailyBlogDetails : Fragment() {
    lateinit var binding: FragmentDailyBlogDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDailyBlogDetailsBinding.inflate(layoutInflater, container, false)

        val imageUrl: String = arguments?.getString("imageUrl").toString()
        val content: String = arguments?.getString("content").toString()
        val author: String = arguments?.getString("author").toString()
        val url: String = arguments?.getString("url").toString()

        Glide.with(binding.root).load(imageUrl).into(binding.imgNewsDetails)
        binding.txtNewsDetailsContent.text = content
        binding.txtNewsDetailsAuthor.text = "- $author"

        // share news
        binding.btnShareNews.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, url);
            startActivity(Intent.createChooser(intent, "Share via"))
        }

        return binding.root
    }
}