package ch.thiercelin.contacts.app.contactlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.thiercelin.contacts.Contact
import ch.thiercelin.contacts.ContactRepository
import ch.thiercelin.contacts.app.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class ContactListUiState(
    val items: List<Contact> = emptyList(),
    val isLoading: Boolean = false,
)

@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val contactRepository: ContactRepository
) : ViewModel() {

    val uiState: StateFlow<ContactListUiState> = contactRepository
        .getContactListStream()
        .map { contactList ->
            ContactListUiState(
                items = contactList
            )
        }.stateIn(
            scope =viewModelScope,
            started = WhileUiSubscribed,
            initialValue = ContactListUiState(isLoading = true)
        )
}