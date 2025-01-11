package com.example.degn.compose.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun CustomEmailField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    onEnterPressed: (() -> Unit)? = null
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.labelMedium
            )
        },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 16.dp)
            .drawBehind {
                val borderWidth = 1.dp.toPx()
                val cornerRadius = 12.dp.toPx()
                drawRoundRect(
                    color = Color.Black,
                    size = size.copy(
                        width = size.width - borderWidth,
                        height = size.height - borderWidth
                    ),
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                    style = Stroke(width = borderWidth)
                )
            },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = Color.Black,
            focusedTextColor = Color.Black,
            focusedPlaceholderColor = Color.Gray
        ),
        textStyle = MaterialTheme.typography.labelMedium,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = if (onEnterPressed != null) ImeAction.Done else ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onEnterPressed?.invoke()
                keyboardController?.hide()
            }
        )
    )
}