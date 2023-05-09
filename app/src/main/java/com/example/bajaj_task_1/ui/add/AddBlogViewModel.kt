package com.example.bajaj_task_1.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bajaj_task_1.databinding.FragmentAddBlogBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class AddBlogViewModel : ViewModel() {

    private val _username = MutableLiveData<String>().apply {
        val documentReference: DocumentReference =
            FirebaseFirestore.getInstance().collection("users")
                .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
        documentReference.addSnapshotListener { v, err ->
            value = v?.getString("username")
        }
    }

    val username: LiveData<String> = _username

    fun addBlogGlobally(map: HashMap<String, Any>, binding: FragmentAddBlogBinding) =
        viewModelScope.launch {
            // code for all blog
            FirebaseFirestore.getInstance().collection("all_blog").document()
                .set(map)
                .addOnSuccessListener {
                    Snackbar.make(binding.root, "Blog Added", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                }
                .addOnFailureListener {
                    Snackbar.make(binding.root, "Error while uploading blog", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                }
        }
}