package com.example.bajaj_task_1.auth

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.example.bajaj_task_1.Dashboard
import com.example.bajaj_task_1.databinding.ActivityRegisterUserBinding
import com.example.bajaj_task_1.util.DialogueBox
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.regex.Pattern

class RegisterUser : AppCompatActivity() {
    lateinit var binding: ActivityRegisterUserBinding

    // regex for password
    private val PASSWORD_PATTERN: Pattern? = Pattern.compile(
        "^" +
                "(?=.*[@#$%^&+=])" +  // at least 1 special character
                "(?=\\S+$)" +  // no white spaces
                ".{4,}" +  // at least 4 characters
                "$"
    )

    // firebase stuff
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterUserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // if user have an account redirect him to login page
        binding.txtOldUser.setOnClickListener {
            startActivity(Intent(this, LoginUser::class.java))
            finish()
        }

        // Get instance of firebase
        auth = FirebaseAuth.getInstance()

        // registering user with firebase auth
        binding.btnSignUp.setOnClickListener {

            val username: String = binding.regUsername.text.toString()
            val email: String = binding.regEmail.text.toString()
            val password: String = binding.regPassword.text.toString()

            // maping username and email
            val map = HashMap<String, Any>()
            map["username"] = username
            map["email"] = email

            if (username.isEmpty()) {
                binding.regUsername.error = "Enter username"
            } else if (email.isEmpty()) {
                binding.regEmail.error = "Enter email"
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.regEmail.error = "Enter proper email"
            } else if (password.isEmpty()) {
                binding.regPassword.error = "Enter password"
            } else if (password.length < 8) {
                binding.regPassword.error = "Password length should not be less than 8"
            } else if (!PASSWORD_PATTERN!!.matcher(password).matches()) {
                binding.regPassword.error = "Password is weak"
            } else {

                DialogueBox.setProgressDialog(this, "wait while registering...")

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        FirebaseFirestore.getInstance()
                            .collection("users")
                            .document(auth.currentUser?.uid.toString())
                            .set(map)
                            .addOnSuccessListener {
                                // dismiss dialogue box
                                DialogueBox.dialog.dismiss()

                                Snackbar.make(view, "Login Successful", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show()
                                startActivity(Intent(this, Dashboard::class.java))
                                finish()
                            }
                            .addOnFailureListener {
                                Snackbar.make(view, "Failed to store data", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show()
                            }
                    }
                    .addOnFailureListener {
                        Snackbar.make(view, "Login UnSuccessful", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show()
                    }
            }
        }
    }
}