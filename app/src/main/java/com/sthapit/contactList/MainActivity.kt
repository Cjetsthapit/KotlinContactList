package com.sthapit.contactList

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract
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
import com.sthapit.contactList.components.ContactsList
import com.sthapit.contactList.components.CustomButton
import com.sthapit.contactList.components.CustomOutlinedTextField
import com.sthapit.contactList.data.Contact
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
            ContactsList(context = context)
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

@SuppressLint("Range")
fun loadContacts(context: ComponentActivity): List<Contact> {

    val contacts = mutableListOf<Contact>()

    context.contentResolver.query(
        ContactsContract.Contacts.CONTENT_URI,
        arrayOf(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY, ContactsContract.Contacts._ID),
        null,
        null,
    )?.use { cursor ->
        if (cursor.moveToFirst()) {
            do {
                val displayName =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY))
                val contactId = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID))

                // Query phone numbers associated with this contact

                context.contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER),
                    "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
                    arrayOf(contactId.toString()),
                    null
                )?.use { phoneCursor ->
                    if (phoneCursor.moveToFirst()) {
                        val phoneNumber =
                            phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        contacts.add(Contact(displayName, phoneNumber, contactId))
                    }
                }
            } while (cursor.moveToNext())
        }
    }

    return contacts
}


@Preview(showBackground = true)
@Composable
fun MainPagePreview() {
    MAPD721A2SrijeetSthapitTheme {
        MainPage(context = ComponentActivity())
    }
}


