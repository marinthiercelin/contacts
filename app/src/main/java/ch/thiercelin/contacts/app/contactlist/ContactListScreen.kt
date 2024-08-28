package ch.thiercelin.contacts.app.contactlist

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.thiercelin.contacts.Contact
import ch.thiercelin.contacts.ContactID

@Composable
fun ContactListScreen(
    viewModel: ContactListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.isLoading) {
        LoadingView()
    } else if (uiState.items.isEmpty()) {
        EmptyListView()
    } else {
        NonEmptyListView(uiState.items)
    }
}

@Composable
fun NonEmptyListView(contactList: List<Contact>) {
    LazyColumn {
        items(contactList) { contact ->
            ContactItem(contact)
        }
    }
}

@Composable
fun ContactItem(contact: Contact) {
    Row {
        Text(contact.name)
    }
}

@Composable
fun LoadingView() {

}

@Composable
fun EmptyListView() {

}

@Preview
@Composable
fun NonEmptyListPreview() {
    NonEmptyListView(contactList = (0..5).map { index ->
        Contact(
            contactID = ContactID("id$index"),
            name = "contact $index"
        )
    })
}