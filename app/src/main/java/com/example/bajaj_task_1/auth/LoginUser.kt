package com.example.bajaj_task_1.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bajaj_task_1.Dashboard
import com.example.bajaj_task_1.databinding.ActivityLoginUserBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginUser : AppCompatActivity() {
    lateinit var binding: ActivityLoginUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginUserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // creating instance for firebase auth
        val auth = FirebaseAuth.getInstance()

        // redirecting user to register page if not registered
        binding.txtNewUser.setOnClickListener {
            startActivity(Intent(this, RegisterUser::class.java))
        }

        // logging user
        binding.btnSignIn.setOnClickListener {

            val email: String = binding.logEmail.text.toString()
            val password: String = binding.logPassword.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    Snackbar.make(view, "Login Successful", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                    startActivity(Intent(this, Dashboard::class.java))
                    finish()
                }
                .addOnFailureListener {

                }
        }
    }
}