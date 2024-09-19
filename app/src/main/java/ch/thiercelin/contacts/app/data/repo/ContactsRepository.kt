package ch.thiercelin.contacts.app.data.repo

import ch.thiercelin.contacts.app.data.model.ContactWithDetails
import ch.thiercelin.contacts.app.data.model.ContactWithoutDetails
import kotlinx.coroutines.flow.Flow

/**
 * Repository for contacts
 */
interface ContactsRepository {

    /**
     * The loaded contacts
     */
    val contacts: Flow<List<ContactWithoutDetails>>

    /**
     * Reload the contacts from the content provider
     */
    suspend fun reloadContacts()

    /**
     * Read details of our specific contacts
     */
    suspend fun getContactDetails(contactId: String): ContactWithDetails?
}