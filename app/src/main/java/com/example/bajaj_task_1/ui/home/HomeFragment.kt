package com.example.bajaj_task_1.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bajaj_task_1.adapter.BlogAdapter
import com.example.bajaj_task_1.databinding.FragmentHomeBinding
import com.example.bajaj_task_1.model.BlogModel
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    lateinit var _binding: FragmentHomeBinding
    lateinit var blogAdapter: BlogAdapter
    private var blogListArray: ArrayList<BlogModel>? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = _binding.root

        val db = FirebaseFirestore.getInstance()

        blogListArray = ArrayList()

        _binding.blogRecyclerView.layoutManager = LinearLayoutManager(context)
        _binding.blogRecyclerView.setHasFixedSize(true)

        blogAdapter = BlogAdapter(blogListArray!!)

        _binding.blogRecyclerView.adapter = blogAdapter

        db.collection("all_blog").get()
            .addOnSuccessListener { queryDocumentSnapshots ->
                if (!queryDocumentSnapshots.isEmpty) {
                    val list = queryDocumentSnapshots.documents
                    for (d in list) {
                        val c: BlogModel? = d.toObject(BlogModel::class.java)
                        blogListArray!!.add(c!!)
                    }
                    blogAdapter.notifyDataSetChanged()
                }
            }.addOnFailureListener { // if we do not get any data or any error we are displaying
                // a toast message that we do not get any data
                Toast.makeText(context, "Fail to get the data.", Toast.LENGTH_SHORT)
                    .show()
            }

        return root
    }
}