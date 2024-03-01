import 'package:dio/dio.dart';
import 'package:logger/logger.dart';
import '../models/item.dart';

// const String baseUrl = 'http://192.168.157.1:2406';
const String baseUrl = 'http://192.168.115.1:2406';
// const String baseUrl = 'http://172.25.64.1:2406';
// const String baseUrl = 'http://172.20.10.2:2406';

class ApiService {
  static final ApiService instance = ApiService._init();
  static final Dio dio = Dio();
  var logger = Logger();

  ApiService._init();

  Future<List<Item>> getSupplies() async {
    logger.log(Level.info, 'getSupplies');
    final response = await dio.get('$baseUrl/medicalsupplies');
    logger.log(Level.info, response.data);
    if (response.statusCode == 200) {
      final result = response.data as List;
      return result.map((e) => Item.fromJson(e)).toList();
    } else {
      logger.e(response.statusMessage);
      throw Exception(response.statusMessage);
    }
  }

  Future<Item> getItemsById(int id) async {
    logger.log(Level.info, 'getMedicalById');
    final response = await dio.get('$baseUrl/medicalsupply/$id');
    logger.log(Level.info, response.data);
    if (response.statusCode == 200) {
      final result = response.data;
      return Item.fromJson(result);
    } else {
      throw Exception(response.statusMessage);
    }
  }

  Future<List<MapEntry<String, int>>> getSuppliesType() async {
    logger.log(Level.info, 'getSuppliesType');
    final response = await dio.get('$baseUrl/suppliestypes');
    logger.log(Level.info, response.data);
    if (response.statusCode == 200) {
      final result = response.data as List;
      var items = result.map((e) => Item.fromJson(e)).toList();
      // return a sorted list of pairs(string, int) that contain total durations of tasks for each month
      var map = <String, int>{};
      items.forEach((element) {
        var type = element.type;
        if (map.containsKey(type)) {
          map[type] = map[type]! + element.quantity!;
        } else {
          map[type] = element.quantity!;
        }
      });
      var list = map.entries.toList();
      return list;
    } else {
      logger.e(response.statusMessage);
      throw Exception(response.statusMessage);
    }
  }

  Future<List<Item>> getSupplyOrders() async {
    logger.log(Level.info, 'getSupplyOrders');
    final response = await dio.get('$baseUrl/supplyorders');
    logger.log(Level.info, response.data);
    if (response.statusCode == 200) {
      final result = response.data as List;
      return result.map((e) => Item.fromJson(e)).toList();
    } else {
      logger.e(response.statusMessage);
      throw Exception(response.statusMessage);
    }
  }

  Future<Item> addItem(Item item) async {
    logger.log(Level.info, 'addItem: $item');
    final response =
        await dio.post('$baseUrl/medicalsupply', data: item.toJsonWithoutId());
    logger.log(Level.info, response.data);
    if (response.statusCode == 200) {
      return Item.fromJson(response.data);
    } else {
      logger.e(response.statusMessage);
      logger.e(response.data);
      // ctx.response.body
      logger.e(response.requestOptions.data);
      throw Exception(response.statusMessage);
    }
  }

  Future<Item> incrementSupplyType(String type) async {
    logger.log(Level.info, 'increment: $type');
    final response =
        await dio.put('$baseUrl/requestsupply/$type');
      logger.log(Level.info, response.data);
    if (response.statusCode == 200) {
      return Item.fromJson(response.data);
    } else {
      logger.e(response.statusMessage);
      logger.e(response.data);
      // ctx.response.body
      logger.e(response.requestOptions.data);
      throw Exception(response.statusMessage);
    }
  }
}
