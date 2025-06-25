package com.example.modsen_tasks_pavel_kolesnikov.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.modsen_tasks_pavel_kolesnikov.ui.event.TaskListEvent
import com.example.modsen_tasks_pavel_kolesnikov.ui.intent.TaskListIntent
import com.example.modsen_tasks_pavel_kolesnikov.ui.viewmodel.TaskListViewModel
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.koinViewModel

@Composable
fun TaskListScreen(
    navController: NavController,
    viewModel: TaskListViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.event.filterIsInstance<TaskListEvent.NavigateToTask>()
            .onEach { event ->
                when (event.task) {
                    "Login Screen" -> navController.navigate("login")
                    "Posts Screen" -> navController.navigate("posts")
                    // add more tasks
                }
            }
            .launchIn(this)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Practice Tasks")
        state.uiModel.tasks.forEach { task ->
            Button(
                onClick = { viewModel.onIntent(TaskListIntent.SelectTask(task = task)) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(task)
            }
        }
    }
}