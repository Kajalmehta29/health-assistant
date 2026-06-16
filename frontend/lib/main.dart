import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import "package:shared_preferences/shared_preferences.dart";
void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Health Assistant',
      debugShowCheckedModeBanner: false,
      home: LoginScreen(),
    );
  }
}

class LoginScreen extends StatelessWidget {
  final TextEditingController emailController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("Login")),
      body: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Column(
          children: [
            TextField(
              controller: emailController,
              decoration: InputDecoration(labelText: "Email"),
            ),
            TextField(
              controller: passwordController,
              decoration: InputDecoration(labelText: "Password"),
              obscureText: true,
            ),
            SizedBox(height: 20),
            ElevatedButton(
              onPressed: () async {
                String email = emailController.text;
                String password = passwordController.text;

                var response = await http.post(
                  Uri.parse("http://10.0.2.2:8080/api/users/login"),
                  headers: {
                    "Content-Type": "application/json",
                  },
                  body: jsonEncode({
                    "email": email,
                    "password": password,
                  }),
                );

                print("STATUS: ${response.statusCode}");
                print("BODY: ${response.body}");

                if (response.statusCode == 200) {
                  var data = jsonDecode(response.body);
                  var token = data['data'];

// 🔥 store token
                  SharedPreferences prefs = await SharedPreferences.getInstance();
                  await prefs.setString('token', token);

                  print("TOKEN SAVED: $token");

// 🔥 navigate
                  Navigator.push(
                    context,
                    MaterialPageRoute(builder: (context) => HomeScreen()),
                  );
                } else {
                  print("Login failed");
                }
              },
              child: Text("Login"),
            ),
          ],
        ),
      ),
    );
  }
}

class HomeScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("Home")),
      body: Center(
        child: Text("Login Successful 🎉"),
      ),
    );
  }
}