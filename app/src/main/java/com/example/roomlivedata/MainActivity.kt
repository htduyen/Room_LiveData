package com.example.roomlivedata

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog.view.*

class MainActivity : AppCompatActivity(), RecyclerviewAdapter.TouchEvent {



    private lateinit var todoViewModel : TodoViewModel
    private lateinit var todoAdapter: RecyclerviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_add.setOnClickListener {
            creator()
        }
        todoViewModel = ViewModelProvider.AndroidViewModelFactory(application).create(TodoViewModel::class.java)

        todoAdapter = RecyclerviewAdapter(this, this)
        rec_totolist.adapter = todoAdapter
        rec_totolist.layoutManager = LinearLayoutManager(this)


        todoViewModel.allTodo.observe(this, Observer {todos ->
            todos.let {
                todoAdapter.setItem(it)
            }
        })
    }
    override fun onClick(todo: Todo) {

    }

    override fun onTouch(todo: Todo) {
        var items = arrayOf("Edit", "Delete")
        AlertDialog.Builder(this)
            .setTitle("What would you like to do?")
            .setItems(items, object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, position: Int) {
                    when(position){
                        0 -> editor(todo)
                        1 -> todoViewModel.delete(todo)
                    }
                }

            }).show()
    }

    fun editor(todo:Todo){
        val layout = layoutInflater.inflate(R.layout.dialog, null)
        layout.edt_title.setText(todo.title)
        layout.edt_content.setText(todo.content)
        val customFragmentDialog = AlertDialog.Builder(this)
            .setTitle("Edit this todo")
            .setView(layout)
            .setPositiveButton("Go", object: DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    todo.title = layout.edt_title.text.toString()
                    todo.content = layout.edt_content.text.toString()

                    todoViewModel.update(todo)
                }

            })
            .create()

        customFragmentDialog.show()
    }

    private fun creator() {
        val layout = layoutInflater.inflate(R.layout.dialog, null)
        val customDialog = AlertDialog.Builder(this)
            .setTitle("Create a new todo")
            .setView(layout)
            .setPositiveButton("Go", object :DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, posotion: Int) {
                    val created = Todo(0, layout.edt_title.text.toString(), layout.edt_content.text.toString())

                    todoViewModel.insert(created)
                }

            }).create()
        customDialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.mnu_clear -> {
                todoViewModel.clearAll()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
