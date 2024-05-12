package com.example.spendsense

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ExpenseAdapter (private var expense: List<Expense>,context: Context) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    private val db: ExpenseDatabaseHelper = ExpenseDatabaseHelper(context)

    class ExpenseViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expense_item, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun getItemCount(): Int = expense.size

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expense[position]


        if (expense.amount != null && expense.amount is Int) {
            holder.titleTextView.text = expense.amount.toString()
        } else {
            // Handle the case where amount is null or not a valid integer
            holder.titleTextView.text = "Invalid Amount"
        }

        holder.contentTextView.text =expense.content
        holder.dateTextView.text =expense.date

        holder.updateButton.setOnClickListener{
            val intent = Intent(holder.itemView.context,UpdateExpenseActivity::class.java).apply {
                putExtra("expense_id",expense.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            db.deleteExpense(expense.id)
            refreshData(db.getAllExpenses())
            Toast.makeText(holder.itemView.context,"Expense Deleted",Toast.LENGTH_SHORT).show()
        }

    }

    fun refreshData(newExpense: List<Expense>){
        expense = newExpense
        notifyDataSetChanged()
    }


}