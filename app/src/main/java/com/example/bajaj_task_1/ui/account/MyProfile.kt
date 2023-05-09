package com.example.bajaj_task_1.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bajaj_task_1.adapter.BlogAdapter
import com.example.bajaj_task_1.adapter.MyBlogsAdapter
import com.example.bajaj_task_1.databinding.FragmentMyProfileBinding
import com.example.bajaj_task_1.model.BlogModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MyProfile : Fragment() {
    lateinit var binding: FragmentMyProfileBinding
    private lateinit var viewModel: MyProfileViewModel

    lateinit var blogAdapter: MyBlogsAdapter
    private var blogListArray: ArrayList<BlogModel>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyProfileBinding.inflate(layoutInflater, container, false)

        // getting my blogs ( locally )
        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()

        blogListArray = ArrayList()

        binding.myBlogRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.myBlogRecyclerView.setHasFixedSize(true)

        blogAdapter = MyBlogsAdapter(blogListArray!!, requireContext())

        binding.myBlogRecyclerView.adapter = blogAdapter

        db.collection("all_blog")
            .whereEqualTo("userUID", auth.currentUser?.uid)
            .get()
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

        // delete blog
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                db.collection("all_blog").whereEqualTo("userUID", auth.currentUser?.uid)
                    .get().addOnCompleteListener {
                        if (it.isSuccessful && !it.result.isEmpty) {
                            val docSnap = it.result.documents[0]
                            val docId: String = docSnap.id
                            db.collection("all_blog")
                                .document(docId)
                                .delete()
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        context,
                                        "Blog Deleted Successfully",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                        }
                    }

                blogAdapter.notifyItemRemoved(viewHolder.position)
            }
        }).attachToRecyclerView(binding.myBlogRecyclerView)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[MyProfileViewModel::class.java]
        viewModel.username.observe(viewLifecycleOwner, Observer {
            binding.profileTxtUsername.text = "Hi, $it"
        })
    }
}