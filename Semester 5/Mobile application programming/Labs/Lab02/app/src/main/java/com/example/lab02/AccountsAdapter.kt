package com.example.lab02

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class AccountsAdapter(private var accounts: List<Account>, context: Context) : RecyclerView.Adapter<AccountsAdapter.AccountViewHolder>() {

    private val db: AccountsDatabaseHelper = AccountsDatabaseHelper(context)

    class AccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ibanTextView: TextView = itemView.findViewById(R.id.ibanTextView)
        val soldTextView: TextView = itemView.findViewById(R.id.soldTextView)
        val currencyTextView: TextView = itemView.findViewById(R.id.currencyTextView)
        val bankTextView: TextView = itemView.findViewById(R.id.bankTextView)
        val accountNameTextView: TextView = itemView.findViewById(R.id.accountnameTextView)

        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.account_item, parent, false)
        return AccountViewHolder(view)
    }

    override fun getItemCount(): Int = accounts.size

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        val account = accounts[position]
        holder.accountNameTextView.text = account.accountName
        holder.ibanTextView.text = account.iban
        holder.soldTextView.text = account.sold
        holder.currencyTextView.text = account.currency
        holder.bankTextView.text = account.bank

        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateAccountActivity::class.java).apply {
                putExtra("account_id", account.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            val dialog = AlertDialog.Builder(holder.itemView.context).setTitle("Info").setMessage("Click YES if you want to proceed")
                .setPositiveButton("Yes", { dialog, i ->
                    db.deleteAccount(account.id)
                    refreshData(db.getAllAccounts())
                    Toast.makeText(holder.itemView.context, "Account deleted", Toast.LENGTH_SHORT).show()
                })
                .setNegativeButton("No", { dialog, i ->
                    dialog.dismiss()
                })

            dialog.show()
        }
    }

    fun refreshData(newAccounts: List<Account>) {
        accounts = newAccounts
        notifyDataSetChanged()
    }
}