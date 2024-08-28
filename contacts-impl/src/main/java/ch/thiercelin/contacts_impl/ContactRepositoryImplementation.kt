package ch.thiercelin.contacts_impl

import ch.thiercelin.contacts.Contact
import ch.thiercelin.contacts.ContactID
import ch.thiercelin.contacts.ContactRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContactRepositoryImplementation @Inject constructor(
    private val dao: ContactDao
): ContactRepository {

    override fun getContactListStream(): Flow<List<Contact>> =
        dao
            .getAll()
            .map { list -> list.map { it.getContact() } }

    override suspend fun createContact(contact: Contact) {
        dao.insert(ContactEntity(contact))
    }

    override suspend fun deleteContact(contactID: ContactID) {
        dao.delete(contactID.value)
    }

    override suspend fun updateContact(contact: Contact) {
        dao.update(ContactEntity(contact))
    }

    override suspend fun getContact(contactID: ContactID): Contact? {
        return dao.getContact(contactID.value)?.getContact()
    }

    override fun getContactStream(contactID: ContactID): Flow<Contact?> {
        return dao.getContactStream(contactID.value).map { it?.getContact() }
    }

}