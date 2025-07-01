package com.example.modsen_tasks_pavel_kolesnikov.ui.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomSearchField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "Search posts...",
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    borderColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    hintColor: Color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
    cursorColor: Color = MaterialTheme.colorScheme.primary,
    selectionBackgroundColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
) {
    val selectionColors = TextSelectionColors(
        handleColor = cursorColor,
        backgroundColor = selectionBackgroundColor
    )

    CompositionLocalProvider(LocalTextSelectionColors provides selectionColors) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
                .clip(RoundedCornerShape(12.dp))
                .background(backgroundColor)
                .border(1.dp, borderColor, RoundedCornerShape(12.dp))
                .padding(12.dp),
            textStyle = TextStyle(
                color = textColor,
                fontSize = 16.sp
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            decorationBox = { innerTextField ->
                if (value.text.isEmpty()) {
                    Text(
                        text = hint,
                        color = hintColor,
                        fontSize = 16.sp
                    )
                }
                innerTextField()
            },
            cursorBrush = SolidColor(cursorColor)
        )
    }
}

@Composable
@Preview
private fun CustomSearchFieldPreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        CustomSearchField(
            value = TextFieldValue(""),
            onValueChange = {}
        )
        Text("Empty with hint")
        Spacer(modifier = Modifier.height(16.dp))
        CustomSearchField(
            value = TextFieldValue("Sample text"),
            onValueChange = {},
            backgroundColor = Color.LightGray,
            borderColor = Color.Blue,
            textColor = Color.Black
        )
        Text("Filled with custom colors")
    }
}