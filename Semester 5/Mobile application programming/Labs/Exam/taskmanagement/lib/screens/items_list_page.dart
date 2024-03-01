import 'package:connectivity_plus/connectivity_plus.dart';
import 'package:flutter/material.dart';
import 'package:logger/logger.dart';
import '../api/api.dart';
import '../models/item.dart';
import '../widgets/message.dart';

import '../api/network.dart';
import '../services/database_helper.dart';

class ItemsListPage extends StatefulWidget {
  final int _id;
  const ItemsListPage(this._id, {super.key});

  @override
  State<StatefulWidget> createState() => _ItemsListPageState();
}

class _ItemsListPageState extends State<ItemsListPage> {
  var logger = Logger();
  bool online = true;
  late Item items;
  bool isLoading = false;
  Map _source = {ConnectivityResult.none: false};
  final NetworkConnectivity _connectivity = NetworkConnectivity.instance;
  String string = '';

  @override
  void initState() {
    super.initState();
    connection();
  }

  void connection() {
    _connectivity.initialize();
    _connectivity.myStream.listen((source) {
      _source = source;
      var newStatus = true;
      switch (_source.keys.toList()[0]) {
        case ConnectivityResult.mobile:
          string =
              _source.values.toList()[0] ? 'Mobile: online' : 'Mobile: offline';
          break;
        case ConnectivityResult.wifi:
          string =
              _source.values.toList()[0] ? 'Wifi: online' : 'Wifi: offline';
          newStatus = _source.values.toList()[0] ? true : false;
          break;
        case ConnectivityResult.none:
        default:
          string = 'Offline';
          newStatus = false;
      }
      if (online != newStatus) {
        online = newStatus;
      }
      getItemsById();
    });
  }

  getItemsById() async {
    if (!mounted) return;
    setState(() {
      isLoading = true;
    });
    logger.log(Level.info, "Online - $online");
    try {
      if (online) {
        items = await ApiService.instance.getItemsById(widget._id);
        // await DatabaseHelper.updateDateItems(widget._id, items);
      }
      // } else {
      //   items = await DatabaseHelper.getItemsById(widget._id);
      // }
    } catch (e) {
      logger.e(e);
      message(context, "Error when retreiving data from server", "Error");
      //items = await DatabaseHelper.getItemsById(widget._id);
    }
    setState(() {
      isLoading = false;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget._id.toString()),
      ),
      body: isLoading
          ? const Center(child: CircularProgressIndicator())
          : Center(
              child: ListView(
              children: [
                ListView.builder(
                  itemBuilder: ((context, index) {
                    return ListTile(
                      title: Text(items.name),
                      subtitle: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text('Supplier: ${items.supplier}'),
                          Text('Details: ${items.details}'),
                          Text('Status: ${items.status}'),
                          Text('Quantity: ${items.quantity}'),
                          Text('Type: ${items.type}'),
                        ],
                      ),
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(18.0),
                        side: const BorderSide(
                          color: Colors.grey,
                          width: 1.0,
                        ),
                      ),
                    );
                  }),
                  itemCount: 1,
                  physics: ScrollPhysics(),
                  shrinkWrap: true,
                  scrollDirection: Axis.vertical,
                  padding: const EdgeInsets.all(10),
                ),
              ],
            )),
    );
  }
}
