import 'package:bank_account_db/service.dart';
import 'package:flutter/material.dart';
import 'my_home_page.dart';
import 'package:provider/provider.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();

  runApp(
      ChangeNotifierProvider(
        create: (_) => AccountService(),
        child: MaterialApp(
          debugShowCheckedModeBanner: false,
          title: 'Bank Accounts',
          theme: ThemeData(
            primarySwatch: Colors.teal,
            visualDensity: VisualDensity.adaptivePlatformDensity,
          ),
          home: MyHomePage("Accounts"),
        ),
      )
  );

  //runApp(const MyApp());
}

// class MyApp extends StatelessWidget {
//   const MyApp({super.key});

//   @override
//   Widget build(BuildContext context) {
//     return MaterialApp(
//       debugShowCheckedModeBanner: false,
//       title: 'Bank Accounts',
//       theme: ThemeData(
//         primarySwatch: Colors.teal,
//         visualDensity: VisualDensity.adaptivePlatformDensity,
//       ),
//       home: MyHomePage("Accounts"),
//     );
//   }
// }