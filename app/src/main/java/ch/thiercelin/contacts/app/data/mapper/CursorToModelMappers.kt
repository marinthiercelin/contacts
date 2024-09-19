package ch.thiercelin.contacts.app.data.mapper

import android.database.Cursor
import android.provider.ContactsContract
import ch.thiercelin.contacts.app.data.model.ContactID
import ch.thiercelin.contacts.app.data.model.ContactWithDetails
import ch.thiercelin.contacts.app.data.model.ContactWithoutDetails

fun Cursor.asContactWithoutDetails(): ContactWithoutDetails {
    val localID = getString(getColumnIndexOrThrow(ContactsContract.Data._ID))
    val name = getString(getColumnIndexOrThrow(ContactsContract.Data.DISPLAY_NAME))
    return ContactWithoutDetails(
        contactID = localID,
        name = name ?: "",
    )
}

fun Cursor.asContactWithDetails(): ContactWithDetails? {
    if (!moveToFirst()) {
        return null
    }
    val localID = getString(getColumnIndexOrThrow(ContactsContract.Data._ID))
    val name = getString(getColumnIndexOrThrow(ContactsContract.Data.DISPLAY_NAME))
    val emailAddresses = mutableListOf<String>()
    val phoneNumbers = mutableListOf<String>()

    do {
        val mimeType = getString(getColumnIndexOrThrow(ContactsContract.Data.MIMETYPE))
        when (mimeType) {
            ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE -> {
                val emailAddress = getString(getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.ADDRESS))
                emailAddresses.add(emailAddress)
            }
            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE -> {
                val phoneNumber = getString(getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
                phoneNumbers.add(phoneNumber)
            }
        }
    } while (moveToNext())
    return ContactWithDetails(
        contactID = localID,
        name = name ?: "",
        emailAddresses = emailAddresses,
        phoneNumbers = phoneNumbers,
    )
}