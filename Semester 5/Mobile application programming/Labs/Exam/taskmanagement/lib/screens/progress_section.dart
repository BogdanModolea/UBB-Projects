import 'package:connectivity_plus/connectivity_plus.dart';
import 'package:flutter/material.dart';
import 'package:logger/logger.dart';
import '../api/api.dart';
import '../widgets/message.dart';

import '../api/network.dart';

class ProgressSection extends StatefulWidget {
  @override
  _ProgressSectionState createState() => _ProgressSectionState();
}

class _ProgressSectionState extends State<ProgressSection> {
  var logger = Logger();
  bool online = true;
  late List<MapEntry<String, int>> supplyType = [];
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
      getSupplyType();
    });
  }

  getSupplyType() async {
    if (!mounted) return;
    setState(() {
      isLoading = true;
    });
    logger.log(Level.info, 'getSupplyType');
    try {
      if (online) {
        supplyType = await ApiService.instance.getSuppliesType();
      } else {
        message(context, "No internet connection", "Error");
      }
    } catch (e) {
      logger.log(Level.error, e.toString());
      message(context, "Error loading items from server and computing corresponding durations", "Error");
    }
    setState(() {
      isLoading = false;
    });
  }

  increment(String type) async {
    if (!mounted) return;
    setState(() {
      isLoading = true;
    });
    logger.log(Level.info, 'incrementSupply');
    try {
      if (online) {
        await ApiService.instance.incrementSupplyType(type);
      } else {
        message(context, "No internet connection", "Error");
      }
    } catch (e) {
      logger.log(Level.error, e.toString());
      message(context, "Error loading items from server and computing corresponding durations", "Error");
    }
    setState(() {
      isLoading = false;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text('Staff Section'),
        ),
        body: isLoading
            ? const Center(child: CircularProgressIndicator())
            : Center(
                child: ListView(children: [
                ListView.builder(
                  itemCount: supplyType.length,
                  itemBuilder: (context, index) {
                    return ListTile(
                      title: Text(supplyType[index].key),
                      subtitle: Text('${supplyType[index].value.toString()} units'),
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(18.0),
                        side: const BorderSide(
                          color: Colors.grey,
                          width: 1.0,
                        ),
                      ),
                      onTap: () {
                        increment(supplyType[index].key);
                      },
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
