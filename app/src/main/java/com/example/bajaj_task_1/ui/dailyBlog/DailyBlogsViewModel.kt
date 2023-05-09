package com.example.bajaj_task_1.ui.dailyBlog

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.bajaj_task_1.model.DailyBlogModel
import kotlinx.coroutines.launch

class DailyBlogsViewModel : ViewModel() {

    private val blogArray = MutableLiveData<List<DailyBlogModel>>()

    fun getBlogsArray(): LiveData<List<DailyBlogModel>> {
        return blogArray
    }

    fun loadData(categories: Array<String>, context: Context) = viewModelScope.launch {
        val queue = Volley.newRequestQueue(context)

        val requests = categories.map { category ->
            val url = "https://inshortsapi.vercel.app/news?category=$category"
            JsonObjectRequest(Request.Method.GET, url, null,
                { response ->
                    val blogJsonArray = response.getJSONArray("data")
                    val blogList = ArrayList<DailyBlogModel>()
                    for (i in 0 until blogJsonArray.length()) {
                        val newsJsonObject = blogJsonArray.getJSONObject(i)
                        val dailyBlogModel = DailyBlogModel(
                            newsJsonObject.getString("content"),
                            newsJsonObject.getString("imageUrl"),
                            newsJsonObject.getString("title"),
                            newsJsonObject.getString("author"),
                            newsJsonObject.getString("url")
                        )
                        blogList.add(dailyBlogModel)
                    }
                    blogArray.postValue(blogArray.value?.shuffled()?.plus(blogList) ?: blogList)

                    // Check if there is only one category selected
                    if (categories.size == 1) {
                        blogArray.postValue(blogList)
                    }

                    Log.d("@debug", "loadData: $response")
                    Log.d("@debug", "loadData: ${response.length()}")
                },
                {
                    Toast.makeText(context, "Failed to get news for $category.", Toast.LENGTH_LONG)
                        .show()
                }
            )
        }

        requests.forEach {
            queue.add(it)
        }
    }
}