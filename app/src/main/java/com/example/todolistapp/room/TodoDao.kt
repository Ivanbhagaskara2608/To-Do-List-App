package com.example.todolistapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getAll(): List<Todo>

    @Insert
    fun insertAll(vararg todo: Todo)

    @Delete
    fun delete(todo: Todo)

    @Query("SELECT * FROM todo WHERE id = :id")
    fun get(id: Int?) : Todo

    @Update
    fun update(todo: Todo)
}