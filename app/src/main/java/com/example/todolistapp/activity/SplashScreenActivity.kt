package com.example.todolistapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.todolistapp.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Set a delay for the splash screen
        val splashDelay: Long = 3000 // 3 seconds

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            // Launch the main activity
            startActivity(intent)
            // Close the splash screen activity
            finish()
        }, splashDelay)
    }
}