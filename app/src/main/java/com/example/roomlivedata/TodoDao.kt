package com.example.roomlivedata

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDao {

    @Query("Select * from todo")
    fun selectAll(): LiveData<List<Todo>>

    @Insert
    suspend fun insert(todo: Todo)

    @Update
    suspend fun update(todo: Todo)

    @Delete
    suspend fun delete(todo:Todo)

    @Query("Delete From todo")
    suspend fun clearAll()

}