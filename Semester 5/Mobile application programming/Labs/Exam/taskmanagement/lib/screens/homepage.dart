import 'package:connectivity_plus/connectivity_plus.dart';
import 'package:flutter/material.dart';
import 'package:logger/logger.dart';
import '../screens/top_section.dart';
import 'progress_section.dart';
import '../widgets/notification.dart';
import 'package:web_socket_channel/web_socket_channel.dart';

import '../api/network.dart';
import '../widgets/message.dart';
import 'main_section.dart';

class Homepage extends StatefulWidget {
  final String _title;
  const Homepage(this._title, {super.key});

  @override
  State<StatefulWidget> createState() => _HomepageState();
}

class _HomepageState extends State<Homepage> {
  // final channel = WebSocketChannel.connect(Uri.parse('ws://192.168.157.1:2406'));
  // final channel = WebSocketChannel.connect(Uri.parse('ws://172.20.10.2:2406'));
  final channel = WebSocketChannel.connect(Uri.parse('ws://192.168.115.1:2406'));
  // final channel = WebSocketChannel.connect(Uri.parse('ws://172.25.64.1:2406'));
  var logger = Logger();
  bool online = true;
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
      logger.log(Level.info, _source);
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
      logger.log(Level.info, "Connection status: $online, $newStatus");
      if (online != newStatus) {
        setState(() {
          online = newStatus;
        });
        if (newStatus) {
          message(context, "Connection restored", "Info");
        } else {
          message(context, "Connection lost", "Info");
        }
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget._title),
      ),
      body: Center(
        child: ListView(
          children: [
            ElevatedButton(
                onPressed: () {
                  Navigator.push(context,
                      MaterialPageRoute(builder: (context) => MainSection()));
                },
                child: const Text('Medical Supply Organizer Section')),
            ElevatedButton(
                onPressed: () {
                  if (!online) {
                    message(context, "No internet connection", "Info");
                    AlertDialog(
                      title: const Text('No internet connection'),
                      content: const Text('Please check your internet connection'),
                      actions: [
                        TextButton(
                          onPressed: () => Navigator.pop(context, 'Cancel'),
                          child: const Text('Cancel'),
                        ),
                        TextButton(
                          onPressed: () => {
                            Navigator.pop(context, 'Retry'),
                            },
                          child: const Text('Retry'),
                        ),
                      ],
                    );
                    return;
                  }
                  Navigator.push(context,
                      MaterialPageRoute(builder: (context) => ProgressSection()));
                },
                child: const Text('Staff Section')),
            ElevatedButton(
                onPressed: () {
                  if (!online) {
                    message(context, "No internet connection", "Info");
                    return;
                  }
                  Navigator.push(context,
                      MaterialPageRoute(builder: (context) => TopSection()));
                },
                child: const Text('Supplier Section')),
            online ? ItemNotification() : const Text(''),
          ],
        ),
      ),
    );
  }

  @override
  void dispose() {
    _connectivity.disposeStream();
    super.dispose();
  }
}
