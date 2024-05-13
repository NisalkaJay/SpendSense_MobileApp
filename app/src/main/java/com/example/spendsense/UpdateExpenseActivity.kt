package com.example.spendsense

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.spendsense.databinding.ActivityUpdateExpenseBinding
import java.text.SimpleDateFormat
import java.util.*


class UpdateExpenseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateExpenseBinding
    private lateinit var db: ExpenseDatabaseHelper
    private var expenseId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = ExpenseDatabaseHelper(this)

        expenseId = intent.getIntExtra("expense_id",-1)
        if(expenseId == -1){
            finish()
            return
        }

        val expense = db.getExpenseByID(expenseId)
        binding.updateAmount.setText(expense.amount.toString())
        binding.updateContentEditText.setText(expense.content)
        binding.updateDateEditText.setText(expense.date)

        binding.updateSaveButton.setOnClickListener{
            val newTitle = binding.updateAmount.text.toString()
            val newTitleInt = newTitle.toIntOrNull() ?: 0
            val newContent = binding.updateContentEditText.text.toString()

            val date = formatDate(binding.updateDateEditText.text.toString())

            val updatedExpense = Expense(expenseId,newTitleInt,newContent,date)
            db.updateExpense(updatedExpense)
            finish()
            Toast.makeText(this,"Changes Saved", Toast.LENGTH_SHORT).show()
        }
    }
}