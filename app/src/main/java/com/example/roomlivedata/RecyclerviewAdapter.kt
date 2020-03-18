package com.example.roomlivedata

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.dialog.view.*
import kotlinx.android.synthetic.main.item_todo.view.*

class RecyclerviewAdapter internal constructor(context: Context,
                                               private val te: TouchEvent):
    RecyclerView.Adapter<RecyclerviewAdapter.TodoViewHolder>() {


    interface TouchEvent {
        fun onClick(todo:Todo)
        fun onTouch(todo:Todo)
    }

    private val inflater :LayoutInflater = LayoutInflater.from(context)
    private var objects = emptyList<Todo>()

    inner class TodoViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        var itemview = inflater.inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(itemview)
    }

    override fun getItemCount(): Int = objects.size


    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val current = objects[position]

        holder.itemView.txt_title.text = current.title
        holder.itemView.txt_content.text = current.content

        holder.itemView.setOnClickListener {
            te.onClick(current)
        }
        holder.itemView.setOnLongClickListener {
            te.onTouch(current)
            return@setOnLongClickListener true
        }
    }
    internal fun getItem(position: Int): Todo {
        return this.objects[position]
    }

    internal fun setItem(items: List<Todo>){
        this.objects = items
        notifyDataSetChanged()
    }
}