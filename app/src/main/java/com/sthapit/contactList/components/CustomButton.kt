package com.sthapit.contactList.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
) {
    ElevatedButton(
        modifier = Modifier
            .padding(end = 8.dp)
            .width(300.dp),
        onClick = onClick,

        ) {
        Text(text)
    }
}