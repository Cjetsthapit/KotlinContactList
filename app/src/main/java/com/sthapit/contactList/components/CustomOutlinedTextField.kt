package com.sthapit.contactList.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 6.dp),
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                color = Color.Gray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold // Adjust text style as needed
            )
        },
        textStyle = TextStyle(
            color = Color.Black, // Text color
            fontSize = 16.sp
        ),
        visualTransformation = visualTransformation,
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = Color.Black, // Cursor color
            focusedBorderColor = Color.Gray, // Border color when focused
            unfocusedBorderColor = Color.LightGray, // Border color when unfocused
        ),
        shape = RoundedCornerShape(8.dp)
    )
}