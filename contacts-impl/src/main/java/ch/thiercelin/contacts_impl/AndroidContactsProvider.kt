package ch.thiercelin.contacts_impl

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import ch.thiercelin.contacts.ContactID
import ch.thiercelin.contacts.ContactsProvider
import ch.thiercelin.contacts.ContactWithDetails
import ch.thiercelin.contacts.ContactWithoutDetails
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidContactsProvider @Inject constructor(
    @ApplicationContext context: Context
): ContactsProvider {
    private val contentResolver: ContentResolver = context.contentResolver

    override suspend fun getContactList(): List<ContactWithoutDetails> {
        val cursor = query(
            uri = ContactsContract.Contacts.CONTENT_URI,
            projection = CONTACTS_PROJECTION,
            sort = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
        )
        return cursor?.let {
            val results = cursor.map { it.asContactWithoutDetails() }
            cursor.close()
            results
        } ?: emptyList();
    }

    private fun<T> Cursor.map(transform: (Cursor) -> T): List<T> {
        val results = mutableListOf<T>()
        while (moveToNext()) {
            results.add(transform(this))
        }
        return results
    }

    private fun Cursor.asContactWithoutDetails(): ContactWithoutDetails {
        val localID = getString(getColumnIndexOrThrow(ContactsContract.Contacts._ID))
        val name = getString(getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
        return ContactWithoutDetails(
            contactID = ContactID(localID),
            name = name ?: "",
        )
    }


    private fun query(
        uri: Uri, // the provider Uri
        projection: Array<String>, // the list of the columns to project
        selection: String? = null,  // the select clause (the WHERE)
        selectionArgs: Array<String>? = null, // the selection arguments (the WHERE arguments to replace)
        sort: String? = null // Definition of sort, offset & limit
    ): Cursor? {
        return contentResolver.query(uri, projection, selection, selectionArgs, sort)
    }

    override suspend fun getContactDetails(contactID: ContactID): ContactWithDetails? {
        TODO("Not yet implemented")
    }

    companion object {

        @JvmStatic
        private val CONTACTS_PROJECTION = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.HAS_PHONE_NUMBER,
        )
    }
}