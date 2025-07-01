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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.modsen_tasks_pavel_kolesnikov.ui.event.CommentsEvent
import com.example.modsen_tasks_pavel_kolesnikov.ui.intent.CommentsIntent
import com.example.modsen_tasks_pavel_kolesnikov.ui.intent.PostsIntent
import com.example.modsen_tasks_pavel_kolesnikov.ui.model.CommentUIModel
import com.example.modsen_tasks_pavel_kolesnikov.ui.model.CommentUIModelMock
import com.example.modsen_tasks_pavel_kolesnikov.ui.model.PostUIModel
import com.example.modsen_tasks_pavel_kolesnikov.ui.screens.common.CustomLoader
import com.example.modsen_tasks_pavel_kolesnikov.ui.screens.common.PostItem
import com.example.modsen_tasks_pavel_kolesnikov.ui.state.CommentsState
import com.example.modsen_tasks_pavel_kolesnikov.ui.state.PostsState
import com.example.modsen_tasks_pavel_kolesnikov.ui.viewmodel.CommentsViewModel
import com.example.modsen_tasks_pavel_kolesnikov.ui.viewmodel.PostsViewModel
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CommentsScreen(
    navController: NavController,
    postId: Int,
) {

    val viewModel = koinViewModel<CommentsViewModel> {
        parametersOf(postId)
    }
    val state: CommentsState by viewModel.state.collectAsStateWithLifecycle()
    val intent: (CommentsIntent) -> Unit by remember { mutableStateOf(viewModel::onIntent) }
    val event by remember { mutableStateOf(viewModel.event) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        event.filterIsInstance<CommentsEvent.ShowError>()
            .onEach {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
            .launchIn(this)
    }

    Content(
        state = state,
        intent = intent,
    )
}

@Composable
@Preview
private fun Content(
    state: CommentsState = CommentsState(),
    intent: (CommentsIntent) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                top = 32.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.uiModel.isLoading && state.uiModel.comments.isEmpty() -> CustomLoader()
            state.uiModel.error != null -> Text(
                text = state.uiModel.error,
                color = MaterialTheme.colorScheme.error
            )

            state.uiModel.post != null -> CommentsContentBlock(
                post = state.uiModel.post,
                comments = state.uiModel.comments,
                intent = intent
            )

            else -> Text("No data available")
        }
    }
}

@Composable
private fun CommentsContentBlock(
    post: PostUIModel,
    comments: List<CommentUIModel>,
    intent: (CommentsIntent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PostItem(post = post)
        Text("Comments", style = MaterialTheme.typography.titleMedium)
        if (comments.isEmpty()) {
            Text("No comments available")
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(comments) { comment ->
                    CommentItem(comment = comment)
                }
            }
        }
    }
}

@Composable
private fun CommentItem(
    comment: CommentUIModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Text(
            text = comment.name,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = comment.email,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Text(
            text = comment.body,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
@Preview
private fun CommentItemPreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        CommentItem(comment = CommentUIModelMock)
    }
}