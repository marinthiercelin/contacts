package ch.thiercelin.contacts.app.data.repo.impl

import ch.thiercelin.contacts.app.data.model.ContactWithDetails
import ch.thiercelin.contacts.app.data.model.ContactWithoutDetails
import ch.thiercelin.contacts.app.data.provider.ContactsProvider
import ch.thiercelin.contacts.app.data.repo.ContactsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(
    private val contactsProvider: ContactsProvider,
): ContactsRepository {

    private val _contactsFlow = MutableStateFlow<List<ContactWithoutDetails>>(emptyList())

    override val contacts: Flow<List<ContactWithoutDetails>> by lazy {
        _contactsFlow
    }

    override suspend fun reloadContacts() {
        _contactsFlow.value = emptyList()
        val result = contactsProvider.getContactList()
        _contactsFlow.value = result
    }

    override suspend fun getContactDetails(
        contactId: String
    ): ContactWithDetails? {
        return contactsProvider.getContactDetails(contactId)
    }
}