package com.hobigibi.ifinancier.datapack.sqlitedb

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Finance.db"
    }

    
    override fun onCreate(db: SQLiteDatabase) {

        // Veritabanı oluşturulduğunda yapılacak işlemler
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Veritabanı güncellendiğinde yapılacak işlemler
    }

    fun createTable(tableName: String) {
        val createTableSQL = "CREATE TABLE IF NOT EXISTS $tableName (id INTEGER PRIMARY KEY AUTOINCREMENT)"
        val db = writableDatabase
        db.execSQL(createTableSQL)
    }

    fun addColumn(tableName: String, columnName: String) {
        val db = writableDatabase
        val cursor = db.rawQuery("PRAGMA table_info($tableName)", null)
        var columnExists = false
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val existingColumnName = cursor.getString(1)
                if (existingColumnName == columnName) {
                    columnExists = true
                    break
                }
            }
            cursor.close()
        }
        if (!columnExists) {
            val addColumnSQL = "ALTER TABLE $tableName ADD COLUMN $columnName TEXT"
            db.execSQL(addColumnSQL)
        }
    }

    fun getData(tableName: String, columnName: String): String? {
        val db = readableDatabase
        val query = "SELECT $columnName FROM $tableName LIMIT 1"
        val cursor = db.rawQuery(query, null)
        var data: String? = null
        if (cursor.moveToFirst())
            data = cursor.getString(0)
        cursor.close()
        return data
    }

    fun editTable(tableName: String, columnName: String, value: String) {
        val query = "SELECT COUNT(*) FROM $tableName"
        val db = writableDatabase
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()

        if (count == 0) {
            val insertSQL = "INSERT INTO $tableName ($columnName) VALUES ('$value')"
            db.execSQL(insertSQL)
        } else {
            val updateSQL = "UPDATE $tableName SET $columnName = '$value'"
             db.execSQL(updateSQL)
        }
        print("\n\n\n”Veri($value) $tableName tablosu'nun $columnName sütununa eklendi!!!\n\n")
    }
}
