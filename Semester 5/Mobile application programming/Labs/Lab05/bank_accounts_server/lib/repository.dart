import 'package:bank_accounts_server/account.dart';
import 'package:sqflite/sqflite.dart';
import 'database.dart';

class AccountRepository {
  List<Account> elements = [];

  List<Account> getAllAccount() {
    return elements;
  }

  void addAccount(Account account) {
    elements.add(account);
  }

  void removeAccount(int id) {
    elements.removeWhere((element) => element.id == id);
  }

  void updateAccount(Account account) {
    elements[elements.indexWhere((element) => element.id == account.id)] =
        account;
  }

  Account getAccountByID(int id) {
    return elements.firstWhere((element) => element.id == id);
  }

  void setData(List<Account> account) {
    elements = account;
  }
}
