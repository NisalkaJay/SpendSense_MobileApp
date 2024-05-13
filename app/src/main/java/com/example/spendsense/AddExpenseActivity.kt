package com.example.spendsense

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.spendsense.databinding.ActivityAddExpenseBinding
import java.util.*

class AddExpenseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddExpenseBinding
    private lateinit var db:ExpenseDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = ExpenseDatabaseHelper(this)

        binding.saveButton.setOnClickListener{
            val title = binding.amount.text.toString()
            val titleAsInteger = title.toIntOrNull() ?: 0
            val  content = binding.contentEditText.text.toString()

            val date = formatDate(binding.dateEditText.text.toString())

            val  expense = Expense(0, titleAsInteger,content, date)
            db.insertExpense(expense)
            finish()
            Toast.makeText(this,"Expense Saved",Toast.LENGTH_SHORT).show()
        }



    }
}
