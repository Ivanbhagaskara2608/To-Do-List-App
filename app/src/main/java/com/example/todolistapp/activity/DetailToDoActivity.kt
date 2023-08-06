package com.example.todolistapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todolistapp.R
import com.example.todolistapp.databinding.ActivityDetailToDoBinding

class DetailToDoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailToDoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailToDoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backImageButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}