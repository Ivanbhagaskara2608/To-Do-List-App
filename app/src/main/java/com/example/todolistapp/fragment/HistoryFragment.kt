package com.example.todolistapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolistapp.R
import com.example.todolistapp.adapter.TodoAdapter
import com.example.todolistapp.databinding.FragmentHistoryBinding
import com.example.todolistapp.databinding.FragmentHomeBinding
import com.example.todolistapp.room.AppDatabase
import com.example.todolistapp.room.Todo
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private var todoList = ArrayList<Todo>()
    private var historyList = ArrayList<Todo>()
    private lateinit var adapter: TodoAdapter
    private lateinit var database: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        INIT DB
        database = AppDatabase.getInstance(requireContext())
//        SET ADAPTER
        adapter = TodoAdapter(requireContext(), "history", historyList)

//        SET RECYCLERVIEW ADAPTER AND LAYOUTMANAGER
        binding.historyRecyclerView.adapter = adapter
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    override fun onResume() {
        super.onResume()
        getHistory()
    }

    private fun getHistory() {
//        CLEAT HISTORY LIST DATA
        historyList.clear()

//        GET CURRENT DATE
        val currentDate = Calendar.getInstance()
        val formatter = SimpleDateFormat("yyyy/M/d", Locale.getDefault())
        val formattedCurrentDate = formatter.format(currentDate.time)

//        CLEAR ALL TODO DATA
        todoList.clear()
//        GET ALL TODO DATA
        todoList.addAll(database.todoDao().getAll())

        for (todo in todoList) {
            val todoDate = Calendar.getInstance()
            todoDate.time = formatter.parse(todo.date)

            if (todoDate.before(currentDate)) {
                if (todo.date != formattedCurrentDate) {
                    historyList.add(todo)
                }
            }
        }

        adapter.notifyDataSetChanged()
    }
}