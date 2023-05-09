package com.example.bajaj_task_1.ui.dailyBlog

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bajaj_task_1.adapter.DailyBlogsAdapter
import com.example.bajaj_task_1.databinding.FragmentDailyBlogsBinding

class DailyBlogs : Fragment() {
    lateinit var binding: FragmentDailyBlogsBinding

    private lateinit var viewModel: DailyBlogsViewModel

    lateinit var strNewsCategory: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDailyBlogsBinding.inflate(layoutInflater, container, false)

        viewModel = ViewModelProvider(this)[DailyBlogsViewModel::class.java]

        // selecting different categories from drop down
        val newsCategory = arrayOf(
            "all",
            "business",
            "sports",
            "world",
            "politics",
            "technology",
            "startup"
        )

        val adapter = DailyBlogsAdapter(emptyList())
        binding.dailyBlogRecyclerView.adapter = adapter
        binding.dailyBlogRecyclerView.layoutManager = GridLayoutManager(context, 2)

        viewModel.getBlogsArray().observe(viewLifecycleOwner) {
            adapter.updateNewsList(it)
        }

        viewModel.loadData(arrayOf(newsCategory[0]), requireContext())

        val newsList: ArrayList<Int> = ArrayList()

        val selectedCategory = BooleanArray(newsCategory.size)

        binding.textViewNewsCategory.setOnClickListener { // Initialize alert dialog
            val builder = AlertDialog.Builder(context)

            // set title
            builder.setTitle("Select Category")

            // set dialog non cancelable
            builder.setCancelable(false)
            builder.setMultiChoiceItems(newsCategory, selectedCategory)
            { _, i, b ->
                // check condition
                if (b) {
                    // when checkbox selected
                    // Add position  in lang list
                    newsList.add(i)
                    // Sort array list
                    newsList.sort()
                } else {
                    // when checkbox unselected
                    // Remove position from langList
                    newsList.remove(Integer.valueOf(i))
                }
            }
            builder.setPositiveButton(
                "OK"
            ) { _, i ->
                // check condition is more than 2 cuisine are selected
                // Initialize string builder
                val stringBuilder = StringBuilder()
                // use for loop
                for (j in 0 until newsList.size) {
                    // concat array value
                    stringBuilder.append(newsCategory[newsList[j]])
                    // check condition
                    if (j != newsList.size - 1) {
                        // When j value  not equal
                        // to lang list size - 1
                        // add comma
                        stringBuilder.append(", ")
                    }
                }
                // set text on textView
                strNewsCategory = stringBuilder.toString()
                binding.textViewNewsCategory.text = strNewsCategory
                Log.d("@debug", "onCreateView: $strNewsCategory")

                val arr: Array<String> = strNewsCategory.split(", ").toTypedArray()

                Log.d("@debug", "onCreateView: $arr")

                adapter.notifyDataSetChanged()

                viewModel.loadData(arr, requireContext())

            }

            builder.setNegativeButton(
                "Cancel"
            ) { dialogInterface, i -> // dismiss dialog
                dialogInterface.dismiss()
            }
            builder.setNeutralButton(
                "Clear All"
            ) { _, i ->
                viewModel.loadData(arrayOf(newsCategory[0]), requireContext())
                // use for loop
                for (j in selectedCategory.indices) {
                    // remove all selection
                    selectedCategory[j] = false
                    newsList.clear()
                    binding.textViewNewsCategory.text = ""
                }
            }
            builder.show()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[DailyBlogsViewModel::class.java]
    }
}