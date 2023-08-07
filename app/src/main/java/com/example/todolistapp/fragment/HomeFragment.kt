package com.example.todolistapp.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolistapp.adapter.TodoAdapter
import com.example.todolistapp.databinding.FragmentHomeBinding
import com.example.todolistapp.room.AppDatabase
import com.example.todolistapp.room.Todo
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var todoList = ArrayList<Todo>()
    private var todayList = ArrayList<Todo>()
    private var upcomingList = ArrayList<Todo>()
    private lateinit var adapterToday: TodoAdapter
    private lateinit var adapterUpcoming: TodoAdapter
    private lateinit var database: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        INIT DATABASE
        database = AppDatabase.getInstance(requireContext())
//        SET ADAPTER TODAY AND UPCOMING
        adapterToday = TodoAdapter(requireContext(), "todays", todayList)
        adapterUpcoming = TodoAdapter(requireContext(), "upcoming", upcomingList)

//        SET RECYCLERVIEW ADAPTER
        binding.todaysRecyclerView.adapter = adapterToday
        binding.upcomingRecyclerView.adapter = adapterUpcoming

//        SET RECYCLERVIEW LAYOUTMANAGER
        binding.todaysRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.upcomingRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    override fun onResume() {
        super.onResume()
//        LOAD DATA
        loadAndSplitTodo()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadAndSplitTodo() {
//        CLEAR LIST DATA TODAY AND UPCOMING
        todayList.clear()
        upcomingList.clear()

//        GET CURRENT DATE
        val currentDate = Calendar.getInstance()
        val formatter = SimpleDateFormat("yyyy/M/d", Locale.getDefault())
        val formattedCurrentDate = formatter.format(currentDate.time)

//        CLEAR ALL TODO DATA
        todoList.clear()
//        GET ALL TODO DATA
        todoList.addAll(database.todoDao().getAll())

//        LOOPING TO CHECK AND SPLIT DATA
        for (todo in todoList) {
            val todoDate = Calendar.getInstance()
            todoDate.time = formatter.parse(todo.date)

//            ADD TODAYS DATA
            if (todo.date == formattedCurrentDate) {
                todayList.add(todo)
            }

//            ADD UPCOMING DATA
            if (todoDate.after(currentDate)) {
                upcomingList.add(todo)
            }
        }

//        SET NOTIFY TO INFORM ADAPTER DATA HAS CHANGED
        adapterToday.notifyDataSetChanged()
        adapterUpcoming.notifyDataSetChanged()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
