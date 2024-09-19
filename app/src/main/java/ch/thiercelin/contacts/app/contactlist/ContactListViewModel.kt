package ch.thiercelin.contacts.app.contactlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.thiercelin.contacts.ContactsProvider
import ch.thiercelin.contacts.ContactWithoutDetails
import ch.thiercelin.contacts.app.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed class ContactListUiState {
    data object Loading: ContactListUiState()
    data object EmptyList: ContactListUiState()
    data class DisplayContactList(val contactList: List<ContactWithoutDetails>): ContactListUiState()
}

@HiltViewModel
class ContactListViewModel @Inject constructor(
    contactRepository: ContactsProvider
) : ViewModel() {
    val uiState: StateFlow<ContactListUiState> = flow {
        emit(contactRepository.getContactList())
    }
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