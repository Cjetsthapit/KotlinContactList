package com.sthapit.contactList.utils

import android.annotation.SuppressLint
import android.content.ContentValues
import android.provider.ContactsContract
import androidx.activity.ComponentActivity
import com.sthapit.contactList.data.Contact

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

fun addContact(context: ComponentActivity, contact: Contact) {

    try {
        val values = ContentValues().apply {
            put(ContactsContract.RawContacts.ACCOUNT_TYPE, "")
            put(ContactsContract.RawContacts.ACCOUNT_NAME, "")

        }

        val rawContactUri =
            context.contentResolver.insert(ContactsContract.RawContacts.CONTENT_URI, values)
        val rawContactId = rawContactUri?.lastPathSegment?.toLongOrNull()
        val displayNameValues = ContentValues().apply {
            put(
                ContactsContract.Data.RAW_CONTACT_ID,
                rawContactId
            )
            put(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
            )
            put(
                ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                contact.displayName
            )
        }
        context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, displayNameValues)
        // Insert phone number
        val phoneNumberValues = ContentValues().apply {
            put(
                ContactsContract.Data.RAW_CONTACT_ID,
                rawContactId
            )
            put(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
            )
            put(
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                contact.phoneNumber
            )
            put(
                ContactsContract.CommonDataKinds.Phone.TYPE,
                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
            )
        }
        context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, phoneNumberValues)
    } catch (e: Exception) {
        // Handle the exception appropriately (e.g., log the error, display a message to the user)
        e.printStackTrace()
    }

}

fun deleteContact(context: ComponentActivity, contactId: Long? = null) {
    val whereClause = "${ContactsContract.CommonDataKinds.Phone._ID} = ?"
    val whereArgs = arrayOf(contactId.toString())
    context.contentResolver.delete(
        ContactsContract.RawContacts.CONTENT_URI,
        whereClause,
        whereArgs
    )
}
