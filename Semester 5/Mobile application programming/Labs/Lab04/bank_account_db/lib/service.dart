import 'package:bank_account_db/account.dart';
import 'package:bank_account_db/database.dart';
import 'package:flutter/material.dart';

class AccountService extends ChangeNotifier {
  final DatabaseHelper dbRepository = DatabaseHelper.instance;

  void populate() {
    List<Account> accounts = [
      Account(
          id: 0,
          iban: 'ROtest01',
          sold: '100',
          currency: 'RON',
          bank: 'BT',
          name: 'Cont 1'
      ),
      Account(
          id: 1,
          iban: 'ROtest02',
          sold: '45',
          currency: 'USD',
          bank: 'BCR',
          name: 'Cont 2'
      ),
      Account(
          id: 2,
          iban: 'ROtest03',
          sold: '333',
          currency: 'EUR',
          bank: 'CEC',
          name: 'Cont 3'
      ),
      Account(
          id: 3,
          iban: 'ROtest04',
          sold: '875',
          currency: 'BTW',
          bank: 'BRD',
          name: 'Cont 4'
      ),
      Account(
          id: 4,
          iban: 'ROtest05',
          sold: '135',
          currency: 'TLR',
          bank: 'Montran',
          name: 'Cont 5'
      ),
    ];

    accounts.forEach((account) {
      dbRepository.add(account);
    });

    notifyListeners();
  }

  void add(Account account) {
    dbRepository.add(account);
    notifyListeners();
  }

  void update(Account account) {
    dbRepository.update(account);
    notifyListeners();
  }

  void remove(int id) {
    dbRepository.remove(id);
    notifyListeners();
  }

  Future<List<Account>> getAccounts() async {
    return await dbRepository.getAccounts();
  }
}