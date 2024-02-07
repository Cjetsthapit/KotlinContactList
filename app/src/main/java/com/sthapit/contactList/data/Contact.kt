package com.sthapit.contactList.data

data class Contact(
    val displayName: String,
    val phoneNumber: String,
    val id: Long? = null
)