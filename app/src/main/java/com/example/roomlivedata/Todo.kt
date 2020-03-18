package com.example.roomlivedata

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class Todo(

    @PrimaryKey(autoGenerate = true)
    var uid: Int,

    var title: String,
    var content: String
){
    override fun toString(): String {
        return "Todo(id = ${uid}, title = ${title}, content = ${content})"
    }
}