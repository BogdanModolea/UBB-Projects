import 'package:flutter/material.dart';
import 'account.dart';

class ModifyAccount extends StatefulWidget {
  final Account account;

  const ModifyAccount(this.account, {Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _ModifyAccountState();
}

class _ModifyAccountState extends State<ModifyAccount> {
  late TextEditingController controllerIban;
  late TextEditingController controllerSold;
  late TextEditingController controllerCurrency;
  late TextEditingController controllerBank;
  late TextEditingController controllerName;

  @override
  void initState() {
    super.initState();
    Account account = widget.account;
    controllerIban = TextEditingController(text: account.iban);
    controllerSold = TextEditingController(text: account.sold);
    controllerCurrency = TextEditingController(text: account.currency);
    controllerBank = TextEditingController(text: account.bank);
    controllerName = TextEditingController(text: account.name);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Edit Account"),
      ),
      body: Container(
        color: Colors.white,
        padding: EdgeInsets.all(16.0),
        child: ListView(
          children: [
            _buildTextField("Iban", controllerIban),
            _buildTextField("Sold", controllerSold),
            _buildTextField("Currency", controllerCurrency),
            _buildTextField("Bank", controllerBank),
            _buildTextField("Account Name", controllerName),
            const SizedBox(height: 16.0),
            ElevatedButton(
              onPressed: _saveAccount,
              child: const Text("Save Account"),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildTextField(String label, TextEditingController controller) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(label),
        TextField(controller: controller),
        const SizedBox(height: 16.0),
      ],
    );
  }

  void _saveAccount() {
    String iban = controllerIban.text;
    String sold = controllerSold.text;
    String currency = controllerCurrency.text;
    String bank = controllerBank.text;
    String name = controllerName.text;

    if (iban.isNotEmpty && sold.isNotEmpty && currency.isNotEmpty && bank.isNotEmpty && name.isNotEmpty) {
      Navigator.pop(
        context,
        Account(
          iban: iban,
          sold: sold,
          currency: currency,
          bank: bank,
          name: name,
        ),
      );
    }
  }
}
