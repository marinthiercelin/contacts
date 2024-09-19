package ch.thiercelin.contacts.app.contactdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.thiercelin.contacts.ContactsProvider
import ch.thiercelin.contacts.app.ContactsDestinationsArgs
import ch.thiercelin.contacts.ContactID
import ch.thiercelin.contacts.ContactWithDetails
import ch.thiercelin.contacts.app.util.WhileUiSubscribed
import com.example.contactsandroid.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * UiState for the Details screen.
 */
sealed class ContactDetailsUiState {
    data object Loading: ContactDetailsUiState()
    data class DisplayContact(val contact: ContactWithDetails): ContactDetailsUiState()
    data class Error(val errorMessage: Int): ContactDetailsUiState()
}

@HiltViewModel
class ContactDetailsViewModel @Inject constructor(
    private val contactRepository: ContactsProvider,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val contactID: String = savedStateHandle[ContactsDestinationsArgs.CONTACT_ID_ARG]!!

    val uiState: StateFlow<ContactDetailsUiState> = flow {
        val contact = contactRepository
            .getContactDetails(ContactID(contactID))
        emit(contact)
    }.map { contact ->
        if (contact == null) {
            ContactDetailsUiState.Error(R.string.contact_not_found_error)
        } else {
            ContactDetailsUiState.DisplayContact(contact)
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = ContactDetailsUiState.Loading
    )

}