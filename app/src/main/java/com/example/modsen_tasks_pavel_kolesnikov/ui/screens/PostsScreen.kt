package com.example.modsen_tasks_pavel_kolesnikov.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.modsen_tasks_pavel_kolesnikov.ui.event.PostsEvent
import com.example.modsen_tasks_pavel_kolesnikov.ui.intent.PostsIntent
import com.example.modsen_tasks_pavel_kolesnikov.ui.model.PostUIModel
import com.example.modsen_tasks_pavel_kolesnikov.ui.screens.common.CustomLoader
import com.example.modsen_tasks_pavel_kolesnikov.ui.screens.common.PostItem
import com.example.modsen_tasks_pavel_kolesnikov.ui.state.PostsState
import com.example.modsen_tasks_pavel_kolesnikov.ui.viewmodel.PostsViewModel
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.koinViewModel

@Composable
fun PostsScreen(
    navController: NavController,
    viewModel: PostsViewModel = koinViewModel()
) {
    val state: PostsState by viewModel.state.collectAsStateWithLifecycle()
    val intent: (PostsIntent) -> Unit by remember { mutableStateOf(viewModel::onIntent) }
    val event by remember { mutableStateOf(viewModel.event) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        event.filterIsInstance<PostsEvent.ShowError>()
            .onEach {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
            .launchIn(this)
    }

    Content(
        state = state,
        intent = intent
    )
}

@Composable
@Preview
private fun Content(
    state: PostsState = PostsState(),
    intent: (PostsIntent) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.uiModel.isLoading -> CustomLoader()
            state.uiModel.error != null -> Text(
                text = state.uiModel.error,
                color = MaterialTheme.colorScheme.error
            )

            state.uiModel.posts.isNotEmpty() -> PostsListBlock(
                posts = state.uiModel.posts,
                intent = intent
            )

            else -> Text("No posts available")
        }
    }
}

@Composable
private fun PostsListBlock(
    posts: List<PostUIModel>,
    intent: (PostsIntent) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(posts) { post ->
            PostItem(post = post)
        }
    }
}