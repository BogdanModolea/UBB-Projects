 package com.example.lab02

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab02.databinding.ActivityMainBinding

 class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: AccountsDatabaseHelper
    private lateinit var accountsAdapter: AccountsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AccountsDatabaseHelper(this)
        accountsAdapter = AccountsAdapter(db.getAllAccounts(), this)

        binding.accountsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.accountsRecyclerView.adapter = accountsAdapter

        binding.addButton.setOnClickListener {
            var intent = Intent(this, AddAccountActivity::class.java)
            startActivity(intent)
        }
    }

     override fun onResume() {
         super.onResume()
         accountsAdapter.refreshData(db.getAllAccounts())
     }
}