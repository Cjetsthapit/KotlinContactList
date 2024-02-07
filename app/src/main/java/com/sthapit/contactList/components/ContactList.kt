package com.sthapit.contactList.components

import androidx.activity.ComponentActivity
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import com.sthapit.contactList.loadContacts

@Composable
fun ContactsList(context: ComponentActivity) {

    val contacts by rememberUpdatedState(
        newValue = loadContacts(context)
    )

    if (contacts.isEmpty()) {
        Text(text = "No contacts available")
    } else {
        LazyColumn {
            items(contacts) { contact ->
                ContactItem(contact, context)
            }
        }
    }
}