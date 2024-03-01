import 'package:connectivity_plus/connectivity_plus.dart';
import 'package:flutter/material.dart';
import 'package:logger/logger.dart';
import '../api/api.dart';
import '../models/item.dart';
import '../widgets/message.dart';

import '../api/network.dart';

class TopSection extends StatefulWidget {
  @override
  _TopSectionState createState() => _TopSectionState();
}

class _TopSectionState extends State<TopSection> {
  var logger = Logger();
  bool online = true;
  late List<Item> supplyOrder = [];
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
      getSupplyOrders();
    });
  }

  getSupplyOrders() async {
    if (!mounted) return;
    setState(() {
      isLoading = true;
    });
    logger.log(Level.info, 'getSupplyOrders');
    try {
      if (online) {
        supplyOrder = await ApiService.instance.getSupplyOrders();
      } else {
        message(context, "No internet connection", "Error");
      }
    } catch (e) {
      logger.log(Level.error, e.toString());
      message(context, "Error loading items from server and computing top 3 categories", "Error");
    }
    setState(() {
      isLoading = false;
    });
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text('Supplier Section'),
        ),
        body: isLoading
            ? const Center(child: CircularProgressIndicator())
            : Center(
            child: ListView(children: [
              ListView.builder(
                itemCount: supplyOrder.length,
                itemBuilder: (context, index) {
                  return ListTile(
                    title: Text(supplyOrder[index].supplier),
                    subtitle: Text(supplyOrder[index].name),
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(18.0),
                      side: const BorderSide(
                        color: Colors.grey,
                        width: 1.0,
                      ),
                    ),
                  );
                },
                physics: ScrollPhysics(),
                shrinkWrap: true,
                scrollDirection: Axis.vertical,
                padding: const EdgeInsets.all(10),
              )
            ])));
  }

  @override
  void dispose() {
    super.dispose();
  }
}
