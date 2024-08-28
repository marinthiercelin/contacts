package ch.thiercelin.contacts.app.contactdetails

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
import ch.thiercelin.contacts.Contact
import ch.thiercelin.contacts.ContactID
import ch.thiercelin.contacts.EmailAddress
import ch.thiercelin.contacts.PhoneNumber
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
private fun ContactDetailsView(contact: Contact) {
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
    Text(text = emailAddress.value)
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
    Text(text = phoneNumber.value)
}

@Composable
private fun ErrorMessageView(errorMessage: Int) {
    Text(text = stringResource(id = errorMessage))
}

@Composable
private fun LoadingView() {
    
}

@Preview
@Composable
fun ContactDetailsPreview() {
    ContactDetailsView(contact = Contact(
        contactID = ContactID("ID"),
        name = "Contact name",
        emailAddresses = listOf(EmailAddress("address@example.com")),
        phoneNumbers = listOf(PhoneNumber("Phone number"))
    ))
}