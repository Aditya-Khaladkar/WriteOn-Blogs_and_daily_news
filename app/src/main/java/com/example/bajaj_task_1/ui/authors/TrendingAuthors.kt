package com.example.bajaj_task_1.ui.authors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bajaj_task_1.adapter.AuthorAdapter
import com.example.bajaj_task_1.databinding.FragmentTrendingAuthorsBinding
import com.example.bajaj_task_1.model.AuthorsModel
import com.google.firebase.firestore.FirebaseFirestore

class TrendingAuthors : Fragment() {
    private lateinit var binding: FragmentTrendingAuthorsBinding

    lateinit var authorAdapter: AuthorAdapter
    private var authorListArray: ArrayList<AuthorsModel>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrendingAuthorsBinding.inflate(layoutInflater, container, false)

        val db = FirebaseFirestore.getInstance()

        authorListArray = ArrayList()

        binding.trendingAuthorRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.trendingAuthorRecyclerView.setHasFixedSize(true)

        authorAdapter = AuthorAdapter(authorListArray!!)

        binding.trendingAuthorRecyclerView.adapter = authorAdapter

        db.collection("all_blog").get()
            .addOnSuccessListener { queryDocumentSnapshots ->
                if (!queryDocumentSnapshots.isEmpty) {
                    val list = queryDocumentSnapshots.documents
                    for (d in list) {
                        val c: AuthorsModel? = d.toObject(AuthorsModel::class.java)
                        authorListArray!!.add(c!!)
                    }
                    authorAdapter.notifyDataSetChanged()
                }
            }.addOnFailureListener { // if we do not get any data or any error we are displaying
                // a toast message that we do not get any data
                Toast.makeText(context, "Fail to get the data.", Toast.LENGTH_SHORT)
                    .show()
            }

        return binding.root
    }
}