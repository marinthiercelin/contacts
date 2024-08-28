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

sealed class ContactListUiState {
    data object Loading: ContactListUiState()
    data object EmptyList: ContactListUiState()
    data class DisplayContactList(val contactList: List<Contact>): ContactListUiState()
}

@HiltViewModel
class ContactListViewModel @Inject constructor(
    contactRepository: ContactRepository
) : ViewModel() {
    val uiState: StateFlow<ContactListUiState> = contactRepository
        .getContactListStream()
        .map { contactList ->
            if (contactList.isEmpty()){
                ContactListUiState.EmptyList
            } else {
                ContactListUiState.DisplayContactList(
                    contactList
                )
            }
        }.stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = ContactListUiState.Loading
        )
}