package ch.thiercelin.contacts_impl

import android.content.ContentResolver
import android.content.Context
import android.provider.ContactsContract
import ch.thiercelin.contacts.ContactID
import ch.thiercelin.contacts.ContactRepository
import ch.thiercelin.contacts.ContactWithDetails
import ch.thiercelin.contacts.ContactWithoutDetails
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidContactRepositoryImplementation @Inject constructor(
    @ApplicationContext context: Context
): ContactRepository {
    private val contentResolver: ContentResolver = context.contentResolver
    override suspend fun getContactList(): List<ContactWithoutDetails> {
        val contactsList = mutableListOf<ContactWithoutDetails>()
        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null
        )
        cursor?.let {
            while (it.moveToNext()) {
                val localID = it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
                val name = it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
                contactsList.add(ContactWithoutDetails(
                    contactID = ContactID(localID),
                    name = name ?: "",
                ))
            }
            cursor.close()
        }
        return contactsList
    }

    override suspend fun getContactDetails(contactID: ContactID): ContactWithDetails? {
        TODO("Not yet implemented")
    }

}