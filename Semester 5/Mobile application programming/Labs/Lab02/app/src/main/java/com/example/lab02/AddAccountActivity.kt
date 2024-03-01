package com.example.lab02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lab02.databinding.ActivityAddAccountBinding

class AddAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddAccountBinding
    private lateinit var db: AccountsDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AccountsDatabaseHelper(this)

        binding.saveButton.setOnClickListener {
            val iban = binding.ibanEditText.text.toString()
            val sold = binding.soldEditText.text.toString()
            val currency = binding.currencyEditText.text.toString()
            val bank = binding.bankEditText.text.toString()
            val accountName = binding.accountnameEditText.text.toString()

            val account = Account(0, iban, sold, currency, bank, accountName)
            db.insertAccount(account)
            finish()
            Toast.makeText(this, "Account Saved", Toast.LENGTH_SHORT).show()
        }
    }
}