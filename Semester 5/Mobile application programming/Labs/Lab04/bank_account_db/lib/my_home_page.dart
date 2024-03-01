import 'package:bank_account_db/add_account.dart';
import 'package:bank_account_db/service.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'account.dart';

import 'modify_account.dart';

class MyHomePage extends StatefulWidget {
  final String _title;

  MyHomePage(this._title);

  @override
  State<StatefulWidget> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget._title),
        centerTitle: true,
      ),
      body: Center(
        child: FutureBuilder<List<Account>>(
            future: Provider.of<AccountService>(context, listen: true).getAccounts(),
            builder: (BuildContext context, AsyncSnapshot<List<Account>> snapshot) {
              if (snapshot.connectionState == ConnectionState.waiting) {
                return const CircularProgressIndicator();
              } else if (snapshot.hasError) {
                return Center(child: Text(snapshot.error.toString()));
              } else if (!snapshot.hasData) {
                return Center(child: Text('Loading...'));
              }
              return snapshot.data!.isEmpty
              ? Center(child: Text('No Accounts in List.'))
              : Center(
                      child: Container(
                      color: Colors.white,
                        child: ListView.builder(
            itemCount: snapshot.data?.length,
            itemBuilder: (context, index) {
              return Padding(
                  padding:  EdgeInsets.only(left: 3, right: 3, top: 10),
                  child: ListTile(
                    textColor: Color.fromRGBO(1,150,128, 296),
                    onTap: () {
                      Navigator.of(context).push(
                        MaterialPageRoute(builder: (context) {
                          return ModifyAccount(snapshot.data![index]);
                        })
                      );
                    },
                    onLongPress: () {
                      removeAccount(snapshot.data![index]);
                    },
                    title: Text(snapshot.data![index].name),
                    subtitle: Text(snapshot.data![index].iban + " | " + snapshot.data![index].bank),
                    trailing: Text.rich(TextSpan(children: [
                      TextSpan(text: "${snapshot.data![index].sold} ${snapshot.data![index].currency}",style: const TextStyle(color:Color.fromRGBO(0, 0, 0, 0.847)))
                    ])),
                  ));
            },
          ),
        ),
        );
      }),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          Navigator.push(context,
                  MaterialPageRoute(builder: (_) => const AddAccount()))
              .then((newAccount) {
            if (newAccount != null) {
              setState(() {
                Provider.of<AccountService>(context, listen: true).add(newAccount);
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

  removeAccount(Account account) {
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
                    Provider.of<AccountService>(context, listen: false).remove(account.id);
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