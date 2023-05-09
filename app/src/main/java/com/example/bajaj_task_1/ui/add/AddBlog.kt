package com.example.bajaj_task_1.ui.add

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.bajaj_task_1.databinding.FragmentAddBlogBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class AddBlog : Fragment() {
    lateinit var binding: FragmentAddBlogBinding

    private lateinit var viewModel: AddBlogViewModel

    // declaring string for author name
    lateinit var authorName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBlogBinding.inflate(layoutInflater, container, false)

        val auth = FirebaseAuth.getInstance()

        binding.addButton.setOnClickListener {

            val title: String = binding.titleEdittext.text.toString()
            val content: String = binding.contentEdittext.text.toString()

            if (title.isEmpty() && content.isEmpty()) {
                Snackbar.make(binding.root, "Title and content Required", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            } else {
                // mapping values
                val map = HashMap<String, Any>()
                map["title"] = title
                map["content"] = content
                map["author"] = authorName
                map["userUID"] = auth.currentUser?.uid.toString()

                viewModel.addBlogGlobally(map, binding)
            }
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddBlogViewModel::class.java)
        viewModel.username.observe(viewLifecycleOwner, Observer {
            authorName = it
        })
    }

}