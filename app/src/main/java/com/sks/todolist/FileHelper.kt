package com.sks.todolist

import android.content.Context
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class FileHelper {

    private val FILENAME = "listinfo.dat"

    fun writeData(item: ArrayList<String>, context: Context) {

        val fileOutputStream: FileOutputStream =
            context.openFileOutput(
                FILENAME,
                Context.MODE_PRIVATE
            ) //openFileOutput() creates a file in the device memory

        val objectOutputStream = ObjectOutputStream(fileOutputStream)
        objectOutputStream.writeObject(item)
        objectOutputStream.close()
    }

    fun readData(context: Context): ArrayList<String> {

        var itemList: ArrayList<String>

        try {
            val fileInputString: FileInputStream = context.openFileInput(FILENAME)
            val objectInputStream = ObjectInputStream(fileInputString)
            itemList = objectInputStream.readObject() as ArrayList<String>
        } catch (exception: FileNotFoundException) {
            itemList = ArrayList()
        }

        return itemList
    }
}