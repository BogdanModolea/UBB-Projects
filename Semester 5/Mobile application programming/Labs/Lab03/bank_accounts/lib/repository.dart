import 'account.dart';

class AccountRepository {
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

  int _nextId = 5;

  List<Account> getAccounts() {
    return List.from(accounts);
  }

  void addAccount(Account newAccount) {
    newAccount.id = _nextId;
    _nextId++;
    accounts.add(newAccount);
  }

  void updateAccount(Account updatedAccount) {
    Account existingAccount = accounts.firstWhere((account) => account.id == updatedAccount.id);
    accounts[accounts.indexOf(existingAccount)] = updatedAccount;
  }

  void removeAccount(Account account) {
    accounts.remove(account);
  }
}
