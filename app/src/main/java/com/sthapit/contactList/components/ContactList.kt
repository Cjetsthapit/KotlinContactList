package com.sthapit.contactList.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.sthapit.contactList.data.Contact

@Composable
fun ContactsList(
    contacts: List<Contact>,
    showDialog: (Contact) -> Unit
) {

    if (contacts.isEmpty()) {
        Text(text = "No contacts available")
    } else {
        LazyColumn {
            items(contacts) { contact ->
                ContactItem(contact, showDialog = showDialog)
            }
        }
    }
}