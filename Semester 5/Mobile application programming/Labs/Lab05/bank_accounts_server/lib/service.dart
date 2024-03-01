import 'package:bank_accounts_server/main.dart';
import 'package:dio/dio.dart';

import 'account.dart';
import 'repository.dart';

class AccountService
{
  static const String _baseUrl = "http://172.20.10.2:8080";
  final Dio _dio = Dio();
  final String appId = DateTime.now().microsecondsSinceEpoch.toString();

  Future<List<Account>> getAllAccount() async {
    final response = await _dio.get('$_baseUrl/accounts');
    final json = response.data;
    var list = (json as List).map((s) => Account.fromMap(s)).toList();
    return list;
  }

  addAccount(Account account) async {
    final response = await _dio.post('$_baseUrl/accounts/$appId', data: account.toMap());
  }

  deleteAccount(int id) async {
    await _dio.delete('$_baseUrl/accounts/$id&$appId');
  }

  updateAccount(Account account) async{
    await _dio.put('$_baseUrl/accounts/$appId', data:account.toMap());
  }



  syncAll(List<Account> accountList) async { 
    accountList.forEach((acc) {
      var check = getAllAccount().then((value) => 
          value.any((element) => element.id == acc.id)
        );
      check.then((found) {
        if(!found){
          addAccount(acc);
        }
      }); 
     });

     getAllAccount().then((value) => 
        value.forEach((element) {
          var check = accountList.any((acc) => acc.id == element.id);
          if(!check){
            deleteAccount(element.id);
          }
        })
      );

      accountList.forEach((acc) {
        var check = getAllAccount().then((value) => 
            value.any((element) => element.id == acc.id)
          );
        check.then((found) {
          if(found){
            updateAccount(acc);
          }
        }); 
      });
  }
}