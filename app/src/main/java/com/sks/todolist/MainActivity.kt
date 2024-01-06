package com.sks.todolist

import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var todoEditText: EditText
    private lateinit var addButton: Button
    private lateinit var listView: ListView

    private var itemList = ArrayList<String>()
    private var fileHelper = FileHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        todoEditText = findViewById(R.id.editText)
        addButton = findViewById(R.id.button)
        listView = findViewById(R.id.listView)

        itemList = fileHelper.readData(this)

        val arrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, itemList)

        listView.adapter = arrayAdapter

        addButton.setOnClickListener {
            val itemName: String = todoEditText.text.toString()
            if (itemName.isNotEmpty()) {
                itemList.add(itemName)
                todoEditText.setText("")
                fileHelper.writeData(itemList, applicationContext)
                arrayAdapter.notifyDataSetChanged()
            } else {
                todoEditText.error = "Task cannot be empty"
            }
        }

        listView.setOnItemClickListener { parent, view, position, id ->
            val alert = AlertDialog.Builder(this)
            alert.setTitle("Delete")
            alert.setMessage("Do you want to delete this item?")
            alert.setCancelable(false)
            alert.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
            alert.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                itemList.removeAt(position)
                arrayAdapter.notifyDataSetChanged()
                fileHelper.writeData(itemList, applicationContext)
            })
            alert.create().show()
        }
    }
}