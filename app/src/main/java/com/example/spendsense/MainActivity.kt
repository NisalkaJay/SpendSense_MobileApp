package com.example.spendsense

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spendsense.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var db: ExpenseDatabaseHelper
    private lateinit var expenseAdapter: ExpenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate((layoutInflater))
        setContentView(binding.root)

        db = ExpenseDatabaseHelper(this)
        expenseAdapter = ExpenseAdapter(db.getAllExpenses(),this)

        binding.expensesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.expensesRecyclerView.adapter = expenseAdapter

        binding.addButton.setOnClickListener{
            val intent = Intent(this,AddExpenseActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        expenseAdapter.refreshData(db.getAllExpenses())
    }
}
