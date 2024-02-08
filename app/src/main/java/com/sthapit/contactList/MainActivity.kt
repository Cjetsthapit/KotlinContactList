package com.sthapit.contactList

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sthapit.contactList.components.AboutSection
import com.sthapit.contactList.components.ContactsList
import com.sthapit.contactList.components.CustomButton
import com.sthapit.contactList.components.CustomOutlinedTextField
import com.sthapit.contactList.data.Contact
import com.sthapit.contactList.ui.theme.MAPD721A2SrijeetSthapitTheme
import com.sthapit.contactList.utils.addContact
import com.sthapit.contactList.utils.deleteContact
import com.sthapit.contactList.utils.loadContacts

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
    var contacts by remember { mutableStateOf(emptyList<Contact>()) }

    LaunchedEffect(Unit) {
        contacts = loadContacts(context)
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
                    Text(text = "Contacts Provider Application")
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
                    addContact(context, newContact)
                    contacts = loadContacts(context)
                    contactName = ""
                    contactNumber = ""
                } else {
                    Toast.makeText(context, "Empty fields.", Toast.LENGTH_SHORT).show()
                }
            }

            Spacer(Modifier.height(10.dp))

            Text(
                "Contacts",
                style = MaterialTheme.typography.headlineMedium,
            )
            var showDialog by remember { mutableStateOf(false) }
            var contactToDelete by remember { mutableStateOf<Contact?>(null) }

            val onDialogDismiss: () -> Unit = {
                showDialog = false
            }

            val onDialogConfirm: () -> Unit = {
                contactToDelete?.let { contact ->
                    deleteContact(context, contact.id)
                    contacts = loadContacts(context)
                }
                showDialog = false
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = onDialogDismiss,
                    title = { Text("Confirm Deletion") },
                    text = { Text("Are you sure you want to delete ${contactToDelete?.displayName}?") },
                    confirmButton = {
                        Button(onClick = onDialogConfirm) {
                            Text("Confirm")
                        }
                    },
                    dismissButton = {
                        Button(onClick = onDialogDismiss) {
                            Text("Cancel")
                        }
                    }
                )
            }

            ContactsList(
                contacts = contacts,
                showDialog = { contactToDelete = it; showDialog = true }
            )

        }

        AboutSection()
    }
}

@Preview(showBackground = true)
@Composable
fun MainPagePreview() {
    MAPD721A2SrijeetSthapitTheme {
        MainPage(context = ComponentActivity())
    }
}


