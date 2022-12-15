package com.example.bankapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankapplication.ui.theme.BankApplicationTheme
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BankApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainView()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView() {
    val gson = Gson()
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var isLogged by rememberSaveable { mutableStateOf(false) }
    var user by remember { mutableStateOf(User("")) }
    Scaffold(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    Text(text = "Bank Application", fontSize = 25.sp)
                },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (!isLogged) {
                Column(
                    modifier = Modifier
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Text(
                            text = "Witaj w Banku Piotrka. ",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                        )
                        Text(
                            text = "Najlepszym banku na świecie. Zaloguj się, aby przejść dalej.",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .weight(1f),
                            value = username,
                            onValueChange = { username = it },
                            label = { Text("Username") },
                            placeholder = { Text("Username") },
                            singleLine = true,
                            textStyle = MaterialTheme.typography.bodyLarge,
                        )
                        OutlinedTextField(
                            modifier = Modifier
                                .weight(1f),
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Password") },
                            placeholder = { Text("Password") },
                            singleLine = true,
                            textStyle = MaterialTheme.typography.bodyLarge,
                            visualTransformation = PasswordVisualTransformation(),
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Button(
                            onClick = {
                                if (username.text.isNotEmpty() && password.text.isNotEmpty()) {
                                    //val login = Login(username.text, password.text)
                                    //user = ApiRequests.userLogin(gson.toJson(login))!!
                                    isLogged = true
                                    //Log.d("MainActivity", "Username: ${user.token}")
                                }
                            }
                        ) {
                            Text("Login")
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isLogged) {
                    LoggedInView()
                }
            }
        }
    }
}

@Composable
fun LoggedInView() {
    var isBlik by rememberSaveable { mutableStateOf(false) }
    Row {
        Text("Witaj *nazwa usera*")
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = "Obecne saldo:",
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
        )
        Text(
            text = "*saldo*",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
        )
    }
    Row {
        // chce zrobić historię transakcji, tylko czy adasiowi będzie się chciało robić więcej endpointów :)
        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedButton(
                modifier = Modifier
                    .padding(10.dp, 0.dp),
                onClick = {
                    isBlik = true
                }
            ) {
                Text(text = "Wygeneruj kod BLIK")
            }
            if (isBlik) {
                Text(
                    text = "*kod BLIK*",
                    style = MaterialTheme.typography.displayLarge,
                    maxLines = 1,
                )
            }
        }
    }
    Row() {
        HistoryView()
    }
}

@Composable
fun HistoryView() {
    val history = remember { mutableStateListOf<String>() }
    history.add("Testowy rekord - 99$")
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(history) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                )
            }
        }
        if (history.isEmpty()) {
            item {
                Text(
                    text = "Brak historii transakcji",
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                )
            }
        }
    }
}