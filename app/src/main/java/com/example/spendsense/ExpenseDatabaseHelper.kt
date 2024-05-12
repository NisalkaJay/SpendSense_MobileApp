package com.example.spendsense

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ExpenseDatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION,){

    companion object{
        private const val DATABASE_NAME = "spendsense.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allexpenses"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "amount"
        private const val COLUMN_CONTENT = "content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY,$COLUMN_TITLE INTEGER, $COLUMN_CONTENT TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertExpense(expense: Expense){
        val db = writableDatabase
        val  values = ContentValues().apply {
            put(COLUMN_TITLE, expense.amount)
            put(COLUMN_CONTENT, expense.content)
        }
        db.insert(TABLE_NAME,null,values)
        db.close()
    }
    fun getAllExpenses(): List<Expense> {
        val expenseList = mutableListOf<Expense>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query,null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

            val expense = Expense(id,title,content)
            expenseList.add(expense)
        }
        cursor.close()
        db.close()
        return expenseList
    }
    fun updateExpense(expense: Expense){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE,expense.amount)
            put(COLUMN_CONTENT,expense.content)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(expense.id.toString())
        db.update(TABLE_NAME,values,whereClause, whereArgs)
        db.close()
    }

    fun getExpenseByID(expenseId:Int):Expense{
        val db = readableDatabase
        val query ="SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID=$expenseId"
        val cursor = db.rawQuery(query,null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

        cursor.close()
        db.close()
        return Expense(id,title,content)
    }

    fun deleteExpense(expenseId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(expenseId.toString())
        db.delete(TABLE_NAME,whereClause, whereArgs)
        db.close()
    }
}