import 'package:logger/logger.dart';
import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart';
import 'package:path_provider/path_provider.dart';

import '../models/item.dart';

class DatabaseHelper {
  static const int _version = 1;
  static const String _databaseName = 'supplies.db';
  static Logger logger = Logger();
  static String _suppliesTableName = 'supplies';

  static Future<Database> _getDB() async {
    final directory = await getApplicationDocumentsDirectory();
    final path = join(directory.path, _databaseName);
    return await openDatabase(path, version: _version,
        onCreate: (db, version) async {
      await db.execute(
          'CREATE TABLE $_suppliesTableName(id INTEGER PRIMARY KEY, name TEXT, supplier TEXT, details TEXT, status TEXT, quantity TEXT, type TEXT)');
    });
  }

  // get all items
  static Future<List<Item>> getItems() async {
    try {
      final db = await _getDB();
      final result = await db.query(_suppliesTableName);
      logger.log(Level.info, "getItems: $result");
      return result.map((e) => Item.fromJson(e)).toList();
    }
    catch (e) {
      logger.log(Level.error, e.toString());
      return [];
    }
  }

  // get all categories
  static Future<List<Item>> getSupplies() async {
    try {
      final db = await _getDB();
      final result = await db.query(_suppliesTableName);
      logger.log(Level.info, "getSupplies: $result");
      return result.map((e) => Item.fromJson(e)).toList();
    }
    catch (e) {
      logger.log(Level.error, e.toString());
      return [];
    }
  }

  // get items by category
  static Future<List<Item>> getItemsById(int id) async {
    try {
      final db = await _getDB();
      final result =
      await db.query(_suppliesTableName, where: 'id = ?', whereArgs: [id]);
      logger.log(Level.info, "getItemsByDate: $result");
      return result.map((e) => Item.fromJson(e)).toList();
    }
    catch (e) {
      logger.log(Level.error, e.toString());
      return [];
    }
  }

  // add item
  static Future<Item> addItem(Item item) async {
    try {
      final db = await _getDB();
      final id = await db.insert(_suppliesTableName, item.toJsonWithoutId(),
          conflictAlgorithm: ConflictAlgorithm.replace);
      logger.log(Level.info, "addItem: $id");
      return item.copy(id: id);
    }
    catch (e) {
      logger.log(Level.error, e.toString());
      return item;
    }
  }

  // update categories in database
  static Future<void> updateSupplies(List<Item> supplies) async {
    try {
      final db = await _getDB();
      await db.delete(_suppliesTableName);
      for (var i = 0; i < supplies.length; i++) {
        await db.insert(_suppliesTableName, {'id': supplies[i].id, 
        'name': supplies[i].name,
        'supplier': supplies[i].supplier,
        'details': supplies[i].details,
        'status': supplies[i].status,
        'quantity': supplies[i].quantity,
        'type': supplies[i].type,
        });
      }
      logger.log(Level.info, "updateSupplies: $supplies");
    }
    catch (e) {
      logger.log(Level.error, e.toString());
    }
  }

  // update a category's items
  static Future<void> updateDateItems(
      int id, List<Item> items) async {
    try {
      final db = await _getDB();
      await db.delete(_suppliesTableName, where: 'id = ?', whereArgs: [id]);
      for (var i = 0; i < items.length; i++) {
        await db.insert(_suppliesTableName, items[i].toJsonWithoutId());
      }
      logger.log(Level.info, "updateDateItems: $id, $items");
    }
    catch (e) {
      logger.log(Level.error, e.toString());
    }
  }

  // close database
  static Future<void> close() async {
    final db = await _getDB();
    await db.close();
  }
}
