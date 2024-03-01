package com.example.lab02

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AccountsDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "accountsapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allaccounts"
        private const val COLUMN_ID = "id"
        private const val COLUMN_IBAN = "iban"
        private const val COLUMN_SOLD = "sold"
        private const val COLUMN_CURRENCY = "currency"
        private const val COLUMN_BANK = "bank"
        private const val COLUMN_ACCOUNT_NAME = "accountname"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_IBAN TEXT, $COLUMN_SOLD TEXT, $COLUMN_CURRENCY TEXT, $COLUMN_BANK TEXT, $COLUMN_ACCOUNT_NAME TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertAccount(account: Account) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_IBAN, account.iban)
            put(COLUMN_SOLD, account.sold)
            put(COLUMN_CURRENCY, account.currency)
            put(COLUMN_BANK, account.bank)
            put(COLUMN_ACCOUNT_NAME, account.accountName)
        }

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllAccounts(): List<Account> {
        val accountList = mutableListOf<Account>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val iban = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IBAN))
            val sold = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SOLD))
            val currency = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CURRENCY))
            val bank = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BANK))
            val accountName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ACCOUNT_NAME))

            val account = Account(id, iban, sold, currency, bank, accountName)
            accountList.add(account)
        }

        cursor.close()
        db.close()

        return accountList
    }

    fun updateAccount(account: Account) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_IBAN, account.iban)
            put(COLUMN_SOLD, account.sold)
            put(COLUMN_CURRENCY, account.currency)
            put(COLUMN_BANK, account.bank)
            put(COLUMN_ACCOUNT_NAME, account.accountName)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(account.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun deleteAccount(accountID: Int) {
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(accountID.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }

    fun getAccountByID(accountID: Int): Account {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $accountID"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val iban = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IBAN))
        val sold = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SOLD))
        val currency = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CURRENCY))
        val bank = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BANK))
        val accountName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ACCOUNT_NAME))

        val account = Account(id, iban, sold, currency, bank, accountName)

        cursor.close()
        db.close()

        return account
    }
}