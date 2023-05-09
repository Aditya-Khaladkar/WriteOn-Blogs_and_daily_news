package com.example.bajaj_task_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.bajaj_task_1.auth.LoginUser
import com.example.bajaj_task_1.auth.RegisterUser
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        Handler().postDelayed(Runnable {
            if (FirebaseAuth.getInstance().currentUser?.uid != null) {
                startActivity(Intent(this, Dashboard::class.java))
                finish()
            } else {
                startActivity(Intent(this, RegisterUser::class.java))
                finish()
            }
        },2000)
    }
}