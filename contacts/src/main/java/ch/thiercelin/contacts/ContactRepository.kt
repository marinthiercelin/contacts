package ch.thiercelin.contacts

import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    fun getContactListStream(): Flow<List<Contact>>
    suspend fun createContact(contact: Contact)
    suspend fun deleteContact(contactID: ContactID)
    suspend fun updateContact(contact: Contact)
}