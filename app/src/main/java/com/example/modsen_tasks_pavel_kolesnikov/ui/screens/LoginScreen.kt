package com.example.modsen_tasks_pavel_kolesnikov.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.modsen_tasks_pavel_kolesnikov.ui.event.LoginEvent
import com.example.modsen_tasks_pavel_kolesnikov.ui.intent.LoginIntent
import com.example.modsen_tasks_pavel_kolesnikov.ui.viewmodel.LoginViewModel
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val event by remember { mutableStateOf(viewModel.event) }

    LaunchedEffect(Unit) {
        event.filterIsInstance<LoginEvent.ShowError>()
            .onEach { event ->
                Toast.makeText(context, event.error, Toast.LENGTH_SHORT).show()
            }
            .launchIn(this)
    }

    LaunchedEffect(Unit) {
        event.filterIsInstance<LoginEvent.NavigateToSuccessScreen>()
            .onEach {
                navController.navigate("success")
            }
            .launchIn(this)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = state.uiModel.login,
            onValueChange = { viewModel.onIntent(LoginIntent.UpdateLogin(it)) },
            label = { Text("Login") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = state.uiModel.password,
            onValueChange = { viewModel.onIntent(LoginIntent.UpdatePassword(it)) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.onIntent(LoginIntent.SubmitLogin) },
            enabled = state.uiModel.login.isNotBlank() && state.uiModel.password.isNotBlank()
                    && !state.uiModel.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (state.uiModel.isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text("Login")
            }
        }
    }
}