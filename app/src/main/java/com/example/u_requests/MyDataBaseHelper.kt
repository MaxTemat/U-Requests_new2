package com.example.u_requests

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast

class MyDataBaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private var context: Context? = context

    override fun onCreate(db: SQLiteDatabase) {
        var query =
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_INFO TEXT)"
        db.execSQL(query)
    }
    override fun onUpgrade(db: SQLiteDatabase, i: Int, i1: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addData(info: String){
        val db = this@MyDataBaseHelper.writableDatabase
        val cv = ContentValues()

        cv.put(COLUMN_INFO, info)
        val result = db.insert(TABLE_NAME, null, cv).toInt()
        if (result == -1)
            Toast.makeText(context, "Failed to added !", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "Added successfully !", Toast.LENGTH_SHORT).show()
    }
    fun readAllData(): Cursor?{
        var cursor: Cursor? = null
        try {
            val query = "SELECT * FROM $TABLE_NAME;"
            val db = this.readableDatabase

            if (db != null)
                cursor = db.rawQuery(query, null)
        }catch (ex: Exception){
            Toast.makeText(context, ex.message, Toast.LENGTH_LONG).show()
        }
        return cursor
    }
    fun updateData(row_id: String, info: String){
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put(COLUMN_INFO, info)

        val result = db.update(TABLE_NAME, cv, "_id=?", arrayOf(row_id))
        if (result == -1)
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "Welcome", Toast.LENGTH_SHORT).show()
    }
    fun deleteData(row_id: String){
        val db = writableDatabase

        val result = db.delete(TABLE_NAME, "_id=?", arrayOf(row_id))
        if (result == -1)
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_LONG).show()
        else
            Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show()
    }
    fun deleteAllData(){
        val db = writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME")
        Toast.makeText(context, "Deleted successfully", Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val DATABASE_NAME = "ApplicationData.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "activity_data"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_INFO = "info"
    }
}