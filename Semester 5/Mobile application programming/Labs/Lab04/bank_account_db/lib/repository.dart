import 'account.dart';
import 'package:bank_account_db/database.dart';

class AccountRepository {
  List<Account> accounts = [];

  List<Account> getAccounts() {
    return List.from(accounts);
  }

  Future<void> addAccount(Account newAccount) async{
    DatabaseHelper.instance.add(newAccount);
    accounts.add(newAccount);
  }

  Future<void> updateAccount(Account updatedAccount) async{
    Account existingAccount = accounts.firstWhere((account) => account.id == updatedAccount.id);
    accounts[accounts.indexOf(existingAccount)] = updatedAccount;
  }

  Future<void> removeAccount(int id) async{
    Account account = accounts.where((element) => element.id == id).first;
    accounts.remove(account);
  }
}
