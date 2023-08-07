package com.example.todolistapp.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.R
import com.example.todolistapp.activity.DetailToDoActivity
import com.example.todolistapp.room.Todo
import org.w3c.dom.Text

class TodoAdapter(val context: Context, private val type: String, var mList: ArrayList<Todo>) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val todoCard: CardView = ItemView.findViewById(R.id.cardLayout)
        val title: TextView = ItemView.findViewById(R.id.titleTextView)
        val date: TextView = ItemView.findViewById(R.id.dateTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        SET LAYOUT THAT WILL BE USED
        val layoutResId = when (type) {
            "todays" -> R.layout.card_view_todays_todo
            "upcoming" -> R.layout.card_view_upcoming_todo
            "history" -> R.layout.card_view_history_todo
            else -> throw IllegalArgumentException("Invalid type: $type")
        }

        val view = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]

        holder.title.text = itemsViewModel.title
        holder.date.text = itemsViewModel.date
        holder.todoCard.setOnClickListener {
            val intent = Intent(context, DetailToDoActivity::class.java)

            intent.putExtra("id", itemsViewModel.id)
            context.startActivity(intent)
            (context as Activity).finish()
        }
    }

}