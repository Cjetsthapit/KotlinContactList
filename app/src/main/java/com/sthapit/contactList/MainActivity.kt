package com.sthapit.contactList

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sthapit.contactList.ui.theme.MAPD721A2SrijeetSthapitTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MAPD721A2SrijeetSthapitTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainPage()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(modifier: Modifier = Modifier) {
    var contactName by remember {
        mutableStateOf("")
    }
    var contactNumber by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            TopAppBar(
                title = {
                    Text(text = "Contacts")
                },
                actions = {
                    // Add actions here if needed
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            CustomOutlinedTextField(
                value = contactName,
                onValueChange = { contactName = it },
                label = "Contact Name"
            )
            CustomOutlinedTextField(
                value = contactNumber,
                onValueChange = { contactNumber = it },
                label = "Contact Number"
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomButton(text = "Load") {


                }
                CustomButton(text = "Save") {

                }

            }
            Text("Contacts")
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(16.dp)

        ) {
            Text(
                text = "Srijeet Sthapit",
                style = MaterialTheme.typography.displaySmall,
                color = Color.White
            )
            Text(
                text = "301365217",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Gray,
                modifier = Modifier
                    .padding(top = 8.dp)
            )


        }

    }
}

@Composable
fun RowScope.CustomButton(text: String, onClick: () -> Unit) {
    ElevatedButton(
        modifier = Modifier
            .weight(1f)
            .padding(end = 8.dp), onClick = onClick
    ) {
        Text(text)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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

@Preview(showBackground = true)
@Composable
fun MainPagePreview() {
    MAPD721A2SrijeetSthapitTheme {
        MainPage()
    }
}