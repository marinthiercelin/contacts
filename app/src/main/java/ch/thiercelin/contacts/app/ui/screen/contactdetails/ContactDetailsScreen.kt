package ch.thiercelin.contacts.app.ui.screen.contactdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.thiercelin.contacts.app.data.model.ContactWithDetails
import ch.thiercelin.contacts.app.data.model.EmailAddress
import ch.thiercelin.contacts.app.data.model.PhoneNumber
import com.example.contactsandroid.R

@Composable
fun ContactDetailsScreen(
    viewModel: ContactDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when(val uiStateLocal = uiState) {
        is ContactDetailsUiState.DisplayContact -> ContactDetailsView(uiStateLocal.contact)
        is ContactDetailsUiState.Error -> ErrorMessageView(uiStateLocal.errorMessage)
        ContactDetailsUiState.Loading -> LoadingView()
    }
}

@Composable
private fun ContactDetailsView(contact: ContactWithDetails) {
    Column {
        Text(text = contact.name)
        EmailAddressListSection(emailAddresses = contact.emailAddresses)
        PhoneNumberListSection(phoneNumbers = contact.phoneNumbers)
    }
}

@Composable
private fun EmailAddressListSection(emailAddresses: List<EmailAddress>) {
    if (emailAddresses.isNotEmpty()) {
        Column {
            Text(text = stringResource(id = R.string.contact_details_email_list_label))
            LazyColumn {
                items(emailAddresses) { emailAddress ->
                    EmailAddressItem(emailAddress)
                }
            }
        }
    }
}

@Composable
private fun EmailAddressItem(emailAddress: EmailAddress) {
    Text(text = emailAddress)
}

@Composable
private fun PhoneNumberListSection(phoneNumbers: List<PhoneNumber>) {
    if (phoneNumbers.isNotEmpty()) {
        Column {
            Text(text = stringResource(id = R.string.contact_details_phone_number_list_label))
            LazyColumn {
                items(phoneNumbers) { phoneNumber ->
                    PhoneNumberItem(phoneNumber)
                }
            }
        }
    }
}

@Composable
private fun PhoneNumberItem(phoneNumber: PhoneNumber) {
    Text(text = phoneNumber)
}

@Composable
private fun ErrorMessageView(errorMessage: Int) {
    Text(text = stringResource(id = errorMessage))
}

@Composable
private fun LoadingView() {
    // TODO : make a loading screen
}

@Preview
@Composable
fun ContactDetailsPreview() {
    ContactDetailsView(contact = ContactWithDetails(
        contactID = "ID",
        name = "Contact name",
        emailAddresses = listOf("address@example.com"),
        phoneNumbers = listOf("Phone number")
    ))
}