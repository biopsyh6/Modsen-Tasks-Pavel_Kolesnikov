package com.example.modsen_tasks_pavel_kolesnikov.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.modsen_tasks_pavel_kolesnikov.ui.event.PostsEvent
import com.example.modsen_tasks_pavel_kolesnikov.ui.intent.PostsIntent
import com.example.modsen_tasks_pavel_kolesnikov.ui.model.PostUIModel
import com.example.modsen_tasks_pavel_kolesnikov.ui.screens.common.CustomLoader
import com.example.modsen_tasks_pavel_kolesnikov.ui.screens.common.CustomSearchField
import com.example.modsen_tasks_pavel_kolesnikov.ui.screens.common.PostItem
import com.example.modsen_tasks_pavel_kolesnikov.ui.state.PostsState
import com.example.modsen_tasks_pavel_kolesnikov.ui.viewmodel.PostsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.androidx.compose.koinViewModel

@Composable
fun PostsCommentsScreen(
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
        intent = intent,
        onPostClick = { post ->
            Log.d("POST", "Navigating to comments with post: $post")
            val postJson = Json.encodeToString(post)
//            navController.currentBackStackEntry?.savedStateHandle?.set("selectedPost", post)
            navController.navigate("comments/${post.id}?post=$postJson")
        }
    )
}

@Composable
@Preview
private fun Content(
    state: PostsState = PostsState(),
    intent: (PostsIntent) -> Unit = {},
    onPostClick: (PostUIModel) -> Unit = {}
) {

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val filteredPosts = state.uiModel.posts.filter {
        it.title.contains(searchQuery.text, ignoreCase = true) ||
                it.body.contains(searchQuery.text, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                start = 16.dp,
                top = 64.dp,
                end = 16.dp,
                bottom = 16.dp
            )
    ) {
        CustomSearchField(
            value = searchQuery,
            onValueChange = { newValue ->
                searchQuery = newValue
            },
            modifier = Modifier.fillMaxWidth()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.uiModel.isLoading && state.uiModel.posts.isEmpty() -> CustomLoader()
                state.uiModel.error != null -> Text(
                    text = state.uiModel.error,
                    color = MaterialTheme.colorScheme.error
                )

                filteredPosts.isNotEmpty() -> PostsListBlock(
                    posts = filteredPosts,
                    intent = intent,
                    onPostClick = onPostClick
                )

                else -> Text("No posts found")
            }
        }
    }
}

@Composable
private fun PostsListBlock(
    posts: List<PostUIModel>,
    intent: (PostsIntent) -> Unit,
    onPostClick: (PostUIModel) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(posts) { post ->
            PostItem(post = post, onClick = { onPostClick(post) })
        }
    }
}