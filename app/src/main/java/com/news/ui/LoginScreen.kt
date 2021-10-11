package com.news.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalAirport
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.news.viewmodel.LoginViewModel
import com.news.R


val labelStyle = TextStyle(
    color = Color.White,
    fontSize = 18.sp
)


@Composable
fun LoginScreen(vm: LoginViewModel, navController: NavController) {
    var username by remember { mutableStateOf("demo") }
    var pass by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Icon(imageVector = Icons.Default.LocalAirport, contentDescription = null)

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 64.dp, end = 64.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Username", style = labelStyle
                )
                OutlinedTextField(
                    value = username,
                    onValueChange = {
                        username = it
                    },
                )
                Text(text = "Password", style = labelStyle)
                OutlinedTextField(
                    value = pass, onValueChange = { pass = it },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )
                TextButton(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        if (pass.isNullOrEmpty()) {
                            Toast.makeText(context, "Please input password", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            vm.login(username, pass)
                            navController.popBackStack()
                        }

                    }) {
                    Text(text = "LOGIN")
                }
            }
        }
    }

}