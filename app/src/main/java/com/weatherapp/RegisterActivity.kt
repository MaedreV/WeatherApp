package com.weatherapp

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.weatherapp.ui.theme.DataField
import com.weatherapp.ui.theme.PasswordField
import com.weatherapp.ui.theme.WeatherAppTheme


class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding -> RegisterPage(
                        modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPage(modifier: Modifier = Modifier) {
    var user by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("")}
    var repeatPassword by rememberSaveable { mutableStateOf("") }

    val activity = LocalContext.current as? Activity
    Column(
        modifier = modifier.padding(16.dp).fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Cadastro",
            fontSize = 24.sp
        )
        DataField(
            value = user,
            onValueChange = { user = it },
            labelText = "Digite seu nome de usuario"
        )
        DataField(
            value = email,
            onValueChange = { email = it },
            labelText = "Digite seu e-mail"
        )

        PasswordField(
            value = password,
            onValueChange = { password = it },
            labelText = "Digite sua senha"
        )
        PasswordField(
            value = repeatPassword,
            onValueChange = { repeatPassword = it },
            labelText = "Digite sua senha novamente"
        )
        Row(modifier = modifier) {
            Button(
                onClick = {
                    Firebase.auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(activity!!) { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(activity,
                                    "Registro OK!", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(activity,
                                    "Registro FALHOU!", Toast.LENGTH_LONG).show()
                            }
                        }
                }
                      ,  enabled = user.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && repeatPassword.isNotEmpty() && password == repeatPassword            ) {
                Text("Cadastro")
            }
            Spacer(modifier = modifier.size(24.dp))
            Button(
                onClick = { user = ""; email = ""; password = ""; repeatPassword = "" }

            ) {
                Text("Limpar")
            }
        }
    }
}