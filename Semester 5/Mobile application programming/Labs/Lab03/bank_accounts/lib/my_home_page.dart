import 'package:bank_accounts/add_account.dart';
import 'package:bank_accounts/repository.dart';
import 'package:flutter/material.dart';
import 'account.dart';

import 'modify_account.dart';

class MyHomePage extends StatefulWidget {
  final String _title;

  MyHomePage(this._title);

  @override
  State<StatefulWidget> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  AccountRepository _accountRepository = AccountRepository();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget._title),
        centerTitle: true,
      ),
      body: Center(
        child: Container(
         color: Colors.white,
          child: ListView.builder(
            itemCount: _accountRepository.accounts.length,
            itemBuilder: (context, index) {
              return Padding(
                  padding:  EdgeInsets.only(left: 3, right: 3, top: 10),
                  child: ListTile(
                    textColor: Color.fromRGBO(1,150,128, 296),
                    onTap: () {
                      Navigator.push(
                              context,
                              MaterialPageRoute(
                                  builder: (_) => ModifyAccount(_accountRepository.accounts[index])))
                          .then((newContact) {
                        if (newContact != null) {
                          setState(() {
                            newContact.id = _accountRepository.accounts[index].id;
                            _accountRepository.updateAccount(newContact);

                            messageResponse(context,
                                newContact.iban + " has been modified!");
                          });
                        }
                      });
                    },
                    onLongPress: () {
                      removeAccount(context, _accountRepository.accounts[index].id);
                    },
                    title: Text(_accountRepository.accounts[index].name),
                    subtitle: Text(_accountRepository.accounts[index].iban + " | " + _accountRepository.accounts[index].bank),
                    trailing: Text.rich(TextSpan(children: [
                      TextSpan(text: "${_accountRepository.accounts[index].sold} ${_accountRepository.accounts[index].currency}",style: const TextStyle(color:Color.fromRGBO(0, 0, 0, 0.847)))
                    ])),
                  ));
            },
          ),
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          Navigator.push(context,
                  MaterialPageRoute(builder: (_) => const AddAccount()))
              .then((newAccount) {
            if (newAccount != null) {
              setState(() {
                _accountRepository.addAccount(newAccount);
                messageResponse(context, newAccount.name + " was added!");
              });
            }
          });
        },
        tooltip: "Add Account",
        child: const Icon(Icons.add),
      ),
    );
  }

  removeAccount(BuildContext context, var id) {
    late Account account;
    for(Account acc in _accountRepository.accounts){
      if(acc.id == id){
        account = acc;
        break;
      }
    }

    showDialog(
        context: context,
        builder: (_) => AlertDialog(
              title: Text("Delete Account"),
              content:
                  Text("${"Are you sure you want to delete " + account.name}?"),
              actions: [
                TextButton(
                  onPressed: () {
                    setState(() {
                      _accountRepository.removeAccount(account);
                      Navigator.pop(context);
                    });
                  },
                  child: const Text(
                    "Delete",
                    style: TextStyle(color: Colors.red),
                  ),
                ),
                TextButton(
                  onPressed: () {
                    Navigator.pop(context);
                  },
                  child: const Text(
                    "Cancel",
                    style: TextStyle(color: Colors.blue),
                  ),
                )
              ],
            ));
  }
}

messageResponse(BuildContext context, String name) {
  showDialog(
      context: context,
      builder: (_) => AlertDialog(
            title: const Text("Update Message"),
            content: Text("Account $name"),
          ));
}