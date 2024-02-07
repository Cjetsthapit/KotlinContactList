package com.sthapit.contactList

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sthapit.contactList.components.CustomButton
import com.sthapit.contactList.components.CustomOutlinedTextField
import com.sthapit.contactList.ui.theme.MAPD721A2SrijeetSthapitTheme

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
                    MainPage(context = this)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(modifier: Modifier = Modifier, context: ComponentActivity) {
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
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(
                title = {
                    Text(text = "Contacts")
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

            CustomButton(text = "Save") {
                if (contactName.isNotBlank() && contactNumber.isNotBlank()) {
                    val newContact = Contact("$contactName", "$contactNumber")

                    contactName = ""
                    contactNumber = ""
                }

            }
            Spacer(Modifier.height(10.dp))
            Text(
                "Contacts",
                style = MaterialTheme.typography.headlineMedium,
            )


        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(16.dp)
        ) {
            Text(
                text = "Srijeet Sthapit",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )

            Text(
                text = "301365217",
                style = MaterialTheme.typography.titleSmall,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}


data class Contact(val displayName: String, val phoneNumber: String, val id: Long? = null)


@Preview(showBackground = true)
@Composable
fun MainPagePreview() {
    MAPD721A2SrijeetSthapitTheme {
        MainPage(context = ComponentActivity())
    }
}


