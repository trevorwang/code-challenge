package com.example.myapplication.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.repo.UserRepo
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.LoginViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


val labelStyle = TextStyle(
    color = Color.White,
    fontSize = 18.sp
)


@Composable
fun LoginScreen(vm: LoginViewModel, navController: NavController) {
    var username by remember { mutableStateOf("adidas_demo") }
    var pass by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    Surface(color = Color.Black) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .padding(64.dp)
            )

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