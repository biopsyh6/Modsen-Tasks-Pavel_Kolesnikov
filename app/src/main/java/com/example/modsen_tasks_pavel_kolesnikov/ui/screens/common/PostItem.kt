package com.example.modsen_tasks_pavel_kolesnikov.ui.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.modsen_tasks_pavel_kolesnikov.ui.model.PostUIModel
import com.example.modsen_tasks_pavel_kolesnikov.ui.model.PostUIModelMock

@Composable
fun PostItem(
    post: PostUIModel,
    modifier: Modifier = Modifier,
    titleSize: TextUnit = 16.sp,
    bodyColor: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = post.title,
                fontSize = titleSize,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = post.body,
                fontSize = 14.sp,
                color = bodyColor
            )
        }
    }
}

@Composable
@Preview
private fun PostItemPreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        PostItem(post = PostUIModelMock)
        Text("Default")
        PostItem(post = PostUIModelMock, titleSize = 20.sp)
        Text("Large Title")
        PostItem(post = PostUIModelMock, bodyColor = Color.Gray)
        Text("Gray body")
    }
}