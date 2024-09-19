package ch.thiercelin.contacts.app.data.provider

import ch.thiercelin.contacts.app.data.model.ContactID
import ch.thiercelin.contacts.app.data.model.ContactWithDetails
import ch.thiercelin.contacts.app.data.model.ContactWithoutDetails

interface ContactsProvider {
    suspend fun getContactList(): List<ContactWithoutDetails>
    suspend fun getContactDetails(contactID: ContactID): ContactWithDetails?
}