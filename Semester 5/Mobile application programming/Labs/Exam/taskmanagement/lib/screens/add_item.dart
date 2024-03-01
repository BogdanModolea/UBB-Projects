import 'package:flutter/material.dart';
import '../models/item.dart';
import '../widgets/message.dart';
import '../widgets/text_box.dart';

class AddItem extends StatefulWidget {
  const AddItem({super.key});

  @override
  State<StatefulWidget> createState() => _AddItemState();
}

class _AddItemState extends State<AddItem> {
  late TextEditingController nameController;
  late TextEditingController supplierController;
  late TextEditingController detailsController;
  late TextEditingController statusController;
  late TextEditingController quantityController;
  late TextEditingController typeController;

  @override
  void initState() {
    nameController = TextEditingController();
    supplierController = TextEditingController();
    detailsController = TextEditingController();
    statusController = TextEditingController();
    quantityController = TextEditingController();
    typeController = TextEditingController();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Add Medical Supply'),
      ),
      body: ListView(
        children: [
          TextBox(nameController, 'Name'),
          TextBox(supplierController, 'Supplier'),
          TextBox(detailsController, 'Details'),
          TextBox(statusController, 'Status'),
          TextBox(quantityController, 'Quantity'),
          TextBox(typeController, 'Type'),
          ElevatedButton(
              onPressed: () {
                String name = nameController.text;
                String supplier = supplierController.text;
                String details = detailsController.text;
                String status = statusController.text;
                String quantity = quantityController.text;
                String type = typeController.text;
                if (name.isNotEmpty &&
                    supplier.isNotEmpty &&
                    details.isNotEmpty &&
                    status.isNotEmpty &&
                    quantity.isNotEmpty &&
                    type.isNotEmpty) {
                  Navigator.pop(
                      context,
                      Item(
                          name: name,
                          supplier: supplier,
                          details: details,
                          status: status,
                          quantity: int.parse(quantity),
                          type: type));
                } else {
                  if (name.isEmpty) {
                    message(context, 'Name is required', "Error");
                  } else if (supplier.isEmpty) {
                    message(context, 'Supplier is required', "Error");
                  } else if (details.isEmpty) {
                    message(context, 'Details is required', "Error");
                  } else if (status.isEmpty) {
                    message(context, 'Status is required', "Error");
                  } else if (quantity.isEmpty) {
                    message(context, 'Quantity must be an integer', "Error");
                  } else if (type.isEmpty) {
                    message(context, 'Type is required', "Error");
                  }
                }
              },
              child: const Text('Save'))
        ],
      ),
    );
  }
}
