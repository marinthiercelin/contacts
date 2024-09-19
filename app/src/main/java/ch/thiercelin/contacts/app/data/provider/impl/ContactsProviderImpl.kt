package ch.thiercelin.contacts.app.data.provider.impl

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import ch.thiercelin.contacts.app.data.mapper.asContactWithDetails
import ch.thiercelin.contacts.app.data.mapper.asContactWithoutDetails
import ch.thiercelin.contacts.app.data.model.ContactID
import ch.thiercelin.contacts.app.data.model.ContactWithDetails
import ch.thiercelin.contacts.app.data.model.ContactWithoutDetails
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ContactsProviderImpl(
    private val contentResolver: ContentResolver,
    private val dispatcher: CoroutineDispatcher,
): ch.thiercelin.contacts.app.data.provider.ContactsProvider {

    override suspend fun getContactList(): List<ContactWithoutDetails> = withContext(dispatcher) {
        val cursor = query(
            uri = ContactsContract.Contacts.CONTENT_URI,
            projection = CONTACTS_PROJECTION,
            sort = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
        )
        cursor?.let {
            val results = cursor.map { it.asContactWithoutDetails() }
            cursor.close()
            results
        } ?: emptyList()
    }

    private fun<T> Cursor.map(transform: (Cursor) -> T): List<T> {
        val results = mutableListOf<T>()
        if(moveToFirst()){
            do {
                results.add(transform(this))
            }while (moveToNext())
        }
        return results
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

    override suspend fun getContactDetails(contactID: ContactID): ContactWithDetails? = withContext(dispatcher) {
        val cursor: Cursor? = query(
            uri = ContactsContract.Data.CONTENT_URI,
            projection = CONTACT_DATA_PROJECTION,
            selection = CONTACT_DETAILS_SELECT,
            selectionArgs = arrayOf(
                contactID,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
                ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE,
            )
        )
        cursor?.asContactWithDetails()
    }

    companion object {

        @JvmStatic
        private val CONTACTS_PROJECTION = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
        )

        @JvmStatic
        private val CONTACT_DATA_PROJECTION = arrayOf(
            ContactsContract.Data._ID,
            ContactsContract.Data.DISPLAY_NAME_PRIMARY,
            ContactsContract.Data.CONTACT_ID,
            ContactsContract.Data.ACCOUNT_TYPE_AND_DATA_SET,
            ContactsContract.Data.MIMETYPE,
            ContactsContract.Data.DATA1,
            ContactsContract.Data.DATA2,
            ContactsContract.Data.DATA3,
        )

        private const val CONTACT_DETAILS_SELECT = "${ContactsContract.Data.CONTACT_ID}=?" +
                " AND ${ContactsContract.Data.MIMETYPE} IN (?,?)"
    }
}