package com.example.todolistapp.activity

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.todolistapp.databinding.ActivityAddToDoBinding
import com.example.todolistapp.room.AppDatabase
import com.example.todolistapp.room.Todo
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddToDoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddToDoBinding
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddToDoBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        INIT DB
        database = AppDatabase.getInstance(applicationContext)

//        SET BACK BUTTON TO MAIN ACTIVITY
        binding.backImageButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

//        GET AND FORMAT CURRENT DATE
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yyyy/M/d", Locale.getDefault())
        val formattedDate = dateFormat.format(currentDate)

//        SET CURRENT DATE IN DATE EDIT TEXT
        binding.dateEditText.setText(formattedDate)

//        SHOW DATE PICKER
        binding.dateEditText.setOnClickListener {
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                val mMonth1 = mMonth + 1
                binding.dateEditText.setText("" + mYear + "/" + mMonth1 + "/" + mDay)
            }, year, month, day).show()
        }

//        SET EVENT ON ADD BUTTON
        binding.addTodoButton.setOnClickListener {
//            CHECK IF THERE'S NULL OR EMPTY IN TITLE & DATE EDIT TEXT
            if (binding.titleEditText.text.isNullOrEmpty()) {
                binding.titleEditText.error = "Title is required!"
                binding.titleEditText.requestFocus()
            } else if (binding.dateEditText.text.isNullOrEmpty()) {
                binding.dateEditText.error = "Date is required"
                binding.dateEditText.requestFocus()
            } else {
//                INSERT TO DB
                database.todoDao().insertAll(Todo(
                    null,
                    binding.titleEditText.text.toString(),
                    binding.dateEditText.text.toString(),
                    binding.descriptionEditText.text.toString()
                ))

//                SHOW TOAST SUCCESS
                Toast.makeText(this, "Your new todo has been recorded", Toast.LENGTH_SHORT).show()

//                CLEAR ALL EDIT TEXT
                binding.titleEditText.text.clear()
                binding.dateEditText.text.clear()
                binding.descriptionEditText.text.clear()
            }
        }
    }

    override fun onBackPressed() {
//        SET BACK TO MAIN ACTIVITY
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}