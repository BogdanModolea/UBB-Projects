import 'dart:convert';

import 'package:bank_accounts_server/repository.dart';
import 'package:bank_accounts_server/service.dart';
import 'package:flutter/material.dart';
import 'my_home_page.dart';
import 'package:stomp_dart_client/stomp.dart';
import 'package:stomp_dart_client/stomp_config.dart';
import 'package:stomp_dart_client/stomp_frame.dart';


AccountRepository repository = AccountRepository();

void onConnect(StompFrame frame){
  print("connected");
  globalKey.currentState?.syncData();
  stompClient.subscribe(
    destination: '/changes/listen',
    callback: (frame) async{
      var jsonData = jsonDecode(frame.body!);
      await globalKey.currentState?.handleChange(jsonData);
    },
  );
}

final service = AccountService();

final stompClient = StompClient(
  config: StompConfig.sockJS(
    url: 'http://172.20.10.2:8080/socket',
    onConnect: onConnect,
    beforeConnect: () async {
      print('waiting to connect...');
      await Future.delayed(const Duration(milliseconds: 200));
      print('connecting...');
    },
    onWebSocketError: (dynamic error) => print(error.toString()),
    stompConnectHeaders: {'Authorization': 'Bearer yourToken'},
    webSocketConnectHeaders: {'Authorization': 'Bearer yourToken'},
  ),
);


void main() {
  runApp(const MyApp());
  stompClient.activate();
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Bank Accounts',
      theme: ThemeData(
        primarySwatch: Colors.teal,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: MyHomePage("Accounts", key: globalKey),
    );
  }
}