package com.example.lab02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lab02.databinding.ActivityUpdateAccountBinding

class UpdateAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateAccountBinding
    private lateinit var db: AccountsDatabaseHelper
    private var accountID: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AccountsDatabaseHelper(this)

        accountID = intent.getIntExtra("account_id", -1)
        if(accountID == -1) {
            finish()
            return
        }

        val account = db.getAccountByID(accountID)
        binding.updateIbanEditText.setText(account.iban)
        binding.updateSoldEditText.setText(account.sold)
        binding.updateCurrencyEditText.setText(account.currency)
        binding.updateBankEditText.setText(account.bank)
        binding.updateAccountnameEditText.setText(account.accountName)

        binding.updateSaveButton.setOnClickListener {
            val newIBAN = binding.updateIbanEditText.text.toString()
            val newSold = binding.updateSoldEditText.text.toString()
            val newCurrency = binding.updateCurrencyEditText.text.toString()
            val newBank = binding.updateBankEditText.text.toString()
            val newAccountName = binding.updateAccountnameEditText.text.toString()

            val updateAccount = Account(accountID, newIBAN, newSold, newCurrency, newBank, newAccountName)
            db.updateAccount(updateAccount)
            finish()
            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
        }
    }
}