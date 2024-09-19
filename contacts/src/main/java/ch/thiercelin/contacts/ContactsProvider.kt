package ch.thiercelin.contacts

interface ContactsProvider {
    suspend fun getContactList(): List<ContactWithoutDetails>
    suspend fun getContactDetails(contactID: ContactID): ContactWithDetails?
}