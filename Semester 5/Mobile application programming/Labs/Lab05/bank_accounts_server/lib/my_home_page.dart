import 'dart:developer';
import 'package:bank_accounts_server/database.dart';
import 'package:bank_accounts_server/main.dart';
import 'package:provider/provider.dart';
import 'service.dart';


import 'add_account.dart';
import 'package:flutter/material.dart';
import 'account.dart';
import 'modify_account.dart';

final GlobalKey<_MyHomePageState> globalKey = GlobalKey<_MyHomePageState>();


class MyHomePage extends StatefulWidget {
  final String _title;

  MyHomePage(this._title, {super.key});

  @override
  State<StatefulWidget> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {

  @override
  void initState() {
    DatabaseHelper.instance.getAccounts().then((value) {
      setState(() {
        // _accountList = value;
        repository.setData(value);
      });
    });
    super.initState();
  }

  Future<void> syncData() async {
    print("syncing with the server");
    DatabaseHelper.instance.getAccounts().then((value) {
      setState(() async {
        await service.syncAll(value);
      });
    });

    await service.getAllAccount().then((value) {
      setState(() {
        repository.setData(value);
      });
    });

    await service.getAllAccount().then((value) {
      setState(() {
        repository.setData(value);
      });
    });
  }

  Future<void> handleChange(dynamic jsonData) async {
    print(jsonData);
    if (jsonData["type"] == "ADD") {
      await DatabaseHelper.instance.add(Account.fromMap(jsonData["content"]));
      setState(() {
        repository.elements.add(Account.fromMap(jsonData["content"]));
        // _accountList.add(Account.fromMap(jsonData["content"]));
      });
      if(jsonData["appId"] != service.appId){
        repository.addAccount(Account.fromMap(jsonData["content"]));
      }
    } else if (jsonData["type"] == "DELETE") {
      await DatabaseHelper.instance.remove(Account.fromMap(jsonData["content"]));
      repository.removeAccount(jsonData["content"]);
      setState(() {
        repository.elements.removeWhere((element) => element.id == jsonData["content"]);
        // _accountList.removeWhere((element) => element.id == jsonData["content"]);
      });
    } else if (jsonData["type"] == "UPDATE") {
      await DatabaseHelper.instance.update(Account.fromMap(jsonData["content"]));
      repository.updateAccount(Account.fromMap(jsonData["content"]));
      setState(() {
        repository.updateAccount(Account.fromMap(jsonData["content"]));
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget._title),
        centerTitle: true,
      ),
      body: ListView.builder(
              itemCount: repository.elements.length,
              // itemCount: _accountList.length,
              itemBuilder: (context, index) {
                return Card(
                  child: ListTile(
                    onTap: () {
                      Navigator.push(
                              context,
                              MaterialPageRoute(
                                  // builder: (_) => ModifyAccount(_accountList[index])))
                                  builder: (_) => ModifyAccount(repository.elements[index])))
                          .then((newContact) {
                        if (newContact != null) {
                          setState(() {
                            newContact.id = repository.elements[index].id;
                          if(stompClient.connected) {
                            print("Updating Account " + newContact.name);
                            service.updateAccount(newContact);
                            //DatabaseHelper.instance.update(newContact);
                            //repository.updateAccount(newContact);
                          } else {
                            print("Updating Account " + newContact.name);
                            DatabaseHelper.instance.update(newContact);
                            repository.updateAccount(newContact);
                          }
                          });
                        }
                      });
                    },
                    onLongPress: () {
                      // removeAccount(context, _accountList[index]);
                      removeAccount(context, repository.elements[index]);
                      log("Deleted account");
                    },
                    // title: Text(_accountList[index].name),
                    title: Text(repository.elements[index].name),
                    // subtitle: Text(_accountList[index].iban + " | " + _accountList[index].bank),
                    subtitle: Text(repository.elements[index].iban + " | " + repository.elements[index].bank),
                    trailing: Text.rich(TextSpan(children: [
                      // TextSpan(text: "${_accountList[index].sold} ${_accountList[index].currency}",style: const TextStyle(color:Color.fromRGBO(0, 0, 0, 0.847)))
                      TextSpan(text: "${repository.elements[index].sold} ${repository.elements[index].currency}",style: const TextStyle(color:Color.fromRGBO(0, 0, 0, 0.847)))
                    ])),
                  ));
            },
          ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          Navigator.push(context,
                  MaterialPageRoute(builder: (_) => const AddAccount()))
              .then((newAccount) {
            if (newAccount != null) {
              setState(() {
                if(stompClient.connected) {
                service.addAccount(newAccount);
                //DatabaseHelper.instance.add(newAccount);
                // repository.addAccount(newAccount);
              }
              else {
                // newAccount.id = null;
                  DatabaseHelper.instance.add(newAccount);
                  repository.addAccount(newAccount);
              }
              });
              
            }
          });
        },
        tooltip: "Add Account",
        child: const Icon(Icons.add),
      ),
    );
  }

  removeAccount(BuildContext context, Account account) {
    showDialog(
        context: context,
        builder: (_) => AlertDialog(
              title: Text("Delete Account"),
              content:
                  Text("${"Are you sure you want to delete " + account.name}?"),
              actions: [
                TextButton(
                  onPressed: () {
                    Navigator.pop(context);
                    setState(() {
                      if(stompClient.connected) {
                      service.deleteAccount(account.id);

                      DatabaseHelper.instance.remove(account);
                      repository.removeAccount(account.id);
                      }
                      else {
                          DatabaseHelper.instance.remove(account);
                          repository.removeAccount(account.id);
                        
                      }
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