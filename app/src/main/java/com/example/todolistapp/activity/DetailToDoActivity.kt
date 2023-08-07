package com.example.todolistapp.activity

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.todolistapp.R
import com.example.todolistapp.databinding.ActivityDetailToDoBinding
import com.example.todolistapp.room.AppDatabase
import com.example.todolistapp.room.Todo
import java.util.Calendar

class DetailToDoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailToDoBinding
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailToDoBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        INIT DB
        database = AppDatabase.getInstance(applicationContext)
//        GET ID FROM INTENT
        var intent = intent.extras
        var id = intent?.getInt("id")
//        GET TO-DO DATA BY ID
        var todo = database.todoDao().get(id)

//        SET BACK TO MAIN ACTIVITY
        binding.backImageButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

//        SET TEXT IN ALL EDIT TEXT USE TODO DATA
        binding.titleEditText.setText(todo.title)
        binding.dateEditText.setText(todo.date)
        binding.descriptionEditText.setText(todo.description)

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

//        SET EVENT ON EDIT BUTTON
        binding.editTodoButton.setOnClickListener {
//            CHECK IF THERE'S NULL OR EMPTY IN TITLE & DATE EDIT TEXT
            if (binding.titleEditText.text.isNullOrEmpty()) {
                binding.titleEditText.error = "Title is required!"
                binding.titleEditText.requestFocus()
            } else if (binding.dateEditText.text.isNullOrEmpty()) {
                binding.dateEditText.error = "Date is required"
                binding.dateEditText.requestFocus()
            } else {
//                UPDATE DATA TO DB
                database.todoDao().update(
                    Todo(
                    id,
                    binding.titleEditText.text.toString(),
                    binding.dateEditText.text.toString(),
                    binding.descriptionEditText.text.toString()
                )
                )
//                SHOW SUCCESS TOAST, THEN BACK TO MAIN ACTIVITY
                Toast.makeText(this, "Your todo has been updated", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

//        SET EVENT ON DELETE BUTTON
        binding.deleteTodoButton.setOnClickListener {
//            DELETE DATA FROM DB
            database.todoDao().delete(todo)
//            SHOW SUCCESS TOAST, THEN BACK TO MAIN ACTIVITY
            Toast.makeText(this, "Your todo has been deleted", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
        //        SET BACK TO MAIN ACTIVITY
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}