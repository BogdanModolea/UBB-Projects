// import 'package:flutter/cupertino.dart';
// import 'service.dart';
// import 'account.dart';

// class AccountListViewModel extends ChangeNotifier {
//   late List<Account> _accountList = <Account>[];
//   final AccountService _accountService = AccountService();

//   List<Account> get accountList => _accountList;

//   Future<void> getAllAccounts() async {
//       var accounts = await _accountService.readAllAccounts();
//       _accountList = <Account>[];
//       accounts.forEach((account) {
//         var acc = Account();
//         acc = Account.fromMap(account);
//         _accountList.add(acc);
//       });
//     notifyListeners();
//   }

//   Future<void> updateAccount(Account account) async {
//     var result = await _accountService.updateAccount(account);
//     if (result != null) {
//       _accountList[_accountList.indexWhere((element) => element.id == account.id)] = account;
//       notifyListeners();
//     }
//   }

//   Future<void> addAccount(Account account) async {
//     var result = await _accountService.saveAccount(account);
//     if (result != null) {
//       _accountList.add(account);
//       notifyListeners();
//     }
//   }

//   Future<void> deleteAccount(int accountId) async {
//     var result = await _accountService.deleteAccount(accountId);
//     if (result != null) {
//       _accountList.removeWhere((account) => account.id == accountId);
//       notifyListeners();
//     }
//   }
// }