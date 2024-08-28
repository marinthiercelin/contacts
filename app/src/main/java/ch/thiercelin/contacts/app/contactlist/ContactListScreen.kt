package ch.thiercelin.contacts.app.contactlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.thiercelin.contacts.Contact
import ch.thiercelin.contacts.ContactID

@Composable
fun ContactListScreen(
    onContactClick: (Contact) -> Unit,
    viewModel: ContactListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (val uiStateLocal = uiState) {
        is ContactListUiState.DisplayContactList -> NonEmptyListView(uiStateLocal.contactList, onContactClick)
        ContactListUiState.EmptyList -> EmptyListView()
        ContactListUiState.Loading ->  LoadingView()
    }
}

@Composable
fun NonEmptyListView(contactList: List<Contact>, onContactClick: (Contact) -> Unit) {
    LazyColumn {
        items(contactList) { contact ->
            ContactItem(contact, onContactClick)
        }
    }
}

@Composable
private fun ContactItem(contact: Contact, onContactClick: (Contact) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onContactClick(contact) }
    ) {
        Text(contact.name)
    }
}

@Composable
private fun LoadingView() {

}

@Composable
private fun EmptyListView() {

}

@Preview
@Composable
fun NonEmptyListPreview() {
    NonEmptyListView(
        onContactClick = {},
        contactList = (0..5).map { index ->
        Contact(
            contactID = ContactID("id$index"),
            name = "contact $index"
        )
    })
}