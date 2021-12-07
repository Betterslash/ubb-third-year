package ro.ubb.composetrainingapp.components.auth

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun Login(navController: NavController) {
    val viewModel = viewModel<LoginViewModel>()
    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var loginStarted by remember { mutableStateOf(false) }
    var loginFailed by remember { mutableStateOf(false) }
    Scaffold(topBar = {
        TopAppBar {
            Text(text = "Login")
        }
    },
    content = { Column(modifier = Modifier.padding(8.dp)) {
        OutlinedTextField(value = username, onValueChange = {username = it}, label = {Text("username")})
        OutlinedTextField(value = password, onValueChange = {password = it}, label = { Text("password")}, visualTransformation = PasswordVisualTransformation())
        Button(onClick = {
            loginStarted = true
            loginFailed = false
            viewModel.login(username = username, password = password)
        }) {Text(text = "Login")}

            if(loginStarted){
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(15.dp)
                )
            }
            if (loginFailed) {
                Text(text = "Login failed")
            }
            val loginResult = viewModel.loginResult.observeAsState().value
            if(loginResult != null){
                if (loginStarted && loginResult.isSuccess) {
                    Log.d(TAG, "navigate")
                    navController.navigate("ideas")
                }
                if (loginResult.isFailure) {
                    loginFailed = true
                }
                loginStarted = false
            }
        }
    },)
}
