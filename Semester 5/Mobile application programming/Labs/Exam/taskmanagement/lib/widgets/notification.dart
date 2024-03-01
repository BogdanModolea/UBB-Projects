import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:flutter/scheduler.dart';
import 'package:logger/logger.dart';
import 'package:web_socket_channel/web_socket_channel.dart';

import '../models/item.dart';
import 'message.dart';

class ItemNotification extends StatelessWidget {
  // final channel = WebSocketChannel.connect(Uri.parse('ws://192.168.157.1:2406'));
  // final channel = WebSocketChannel.connect(Uri.parse('ws://172.20.10.2:2406'));
  final channel = WebSocketChannel.connect(Uri.parse('ws://192.168.115.1:2406'));
  // final channel = WebSocketChannel.connect(Uri.parse('ws://172.25.64.1:2406'));


  ItemNotification({super.key});

  @override
  Widget build(BuildContext context) {
    return StreamBuilder(
        builder: (context, snapshot) {
          SchedulerBinding.instance.addPostFrameCallback((_) {
            if (snapshot.hasData) {
              var item = Item.fromJson(jsonDecode(snapshot.data.toString()));
              Logger().log(Level.info, item);
              message(context, item.toString(), "Item added");
            }
          });
          return const Text('');
        },
        stream: channel.stream.asBroadcastStream());
  }
}
