package ch.thiercelin.contacts

interface ContactRepository {
    suspend fun getContactList(): List<ContactWithoutDetails>
    suspend fun getContactDetails(contactID: ContactID): ContactWithDetails?
}