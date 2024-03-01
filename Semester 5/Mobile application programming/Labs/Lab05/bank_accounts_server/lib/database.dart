import 'dart:async';
import 'dart:io';
import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart';
import 'package:path_provider/path_provider.dart';

import 'account.dart';


class DatabaseHelper {
  DatabaseHelper._privateConstructor();
  static final DatabaseHelper instance = DatabaseHelper._privateConstructor();

  static Database? _database;
  Future<Database> get database async => _database ??= await _initDatabase();

  Future<Database> _initDatabase() async {
    Directory documentsDirectory = await getApplicationDocumentsDirectory();
    String path = join(documentsDirectory.path, 'accounts2.db');
    return await openDatabase(
      path,
      version: 1,
      onCreate: _onCreate,
    );
  }

  Future _onCreate(Database db, int version) async {
    await db.execute('''
      CREATE TABLE accounts2(
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
    var accounts = await db.query('accounts2');
    List<Account> accountList = accounts.isNotEmpty
        ? accounts.map((c) => Account.fromMap(c)).toList()
        : [];

    return accountList;
  }

  Future<int> add(Account account) async {
    Database db = await instance.database;
    return await db.insert('accounts2', account.toMap());
  }

  Future<int> remove(Account account) async {
    Database db = await instance.database;
    return await db.delete('accounts2', where: 'id = ?', whereArgs: [account.id]);
  }

  Future<int> update(Account account) async {
    Database db = await instance.database;
    return await db.update('accounts2', account.toMap(),
        where: "id = ?", whereArgs: [account.id]);
  } 

  
}