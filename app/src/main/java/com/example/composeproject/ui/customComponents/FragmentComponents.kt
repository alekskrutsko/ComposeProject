package com.example.composeproject.ui.customComponents

import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CustomText(
    text: String,
    modifier: Modifier
){
    Text(
        text = text,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    text: String,
    modifier: Modifier,
    onValueChange: (String) -> Unit,
){
    TextField(
        value = text,
        onValueChange = onValueChange,
        modifier = modifier
    )
}


@Composable
fun CustomButton(
    text: String,
    modifier: Modifier,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(text = text)
    }
}