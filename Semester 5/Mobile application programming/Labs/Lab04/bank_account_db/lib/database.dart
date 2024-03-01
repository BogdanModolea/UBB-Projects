import 'dart:async';
import 'dart:io';
import 'package:bank_account_db/account.dart';
import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart';
import 'package:path_provider/path_provider.dart';

class DatabaseHelper {
  DatabaseHelper._privateConstructor();
  static final DatabaseHelper instance = DatabaseHelper._privateConstructor();

  late Database _database;

  Future<Database> get database async {
    _database = await _initDatabase();
    return _database;
  }

  
  Future<Database> _initDatabase() async {
    Directory documentsDirectory = await getApplicationDocumentsDirectory();
    String path = join(documentsDirectory.path, 'accounts.db');
    return await openDatabase(
      path,
      version: 1,
      onCreate: _onCreate,
    );
  }

  Future _onCreate(Database db, int version) async {
    await db.execute('''
      CREATE TABLE accounts(
          id INTEGER PRIMARY KEY,
          iban TEXT,
          sold TEXT,
          currency TEXT,
          bank TEXT,
          name TEXT
      )
      ''');
  }

  Future<List<Account>> getAccounts() async {
    Database db = await instance.database;
    var accounts = await db.query('accounts', orderBy: 'name');
    List<Account> accountList = accounts.isNotEmpty
        ? accounts.map((c) => Account.fromMap(c)).toList()
        : [];

    return accountList;
  }

  Future<int> add(Account account) async {
    Database db = await instance.database;
    return await db.insert('accounts', account.toMap());
  }

  Future<int> remove(int id) async {

    Database db = await instance.database;
    return await db.delete('accounts', where: 'id = ?', whereArgs: [id]);
  }

  Future<int> update(Account account) async {
    Database db = await instance.database;
    return await db.update('accounts', account.toMap(),
        where: "id = ?", whereArgs: [account.id]);
  } 
}