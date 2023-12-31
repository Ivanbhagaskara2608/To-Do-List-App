package com.example.todolistapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.todolistapp.R
import com.example.todolistapp.databinding.ActivityMainBinding
import com.example.todolistapp.fragment.HistoryFragment
import com.example.todolistapp.fragment.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        SET HOME FRAGMENT AS DEFAULT
        setFragment(HomeFragment())

//        SET BOTTOM NAV LISTENER
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeNavigation -> {
                    setFragment(HomeFragment())
                    true
                }
                R.id.historyNavigation -> {
                    setFragment(HistoryFragment())
                    true
                }
                else -> false
            }
        }

//        SET EVENT ON ADD TODO BUTTON
        binding.addTodoButton.setOnClickListener {
            val intent = Intent(this, AddToDoActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

//    FUNCTION TO REPLACE FRAGMENT
    private fun  setFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }
}