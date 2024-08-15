package ch.thiercelin.contacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

private val dummyContact = Contact( contactID = "ID", name = "Jane Doe", emailAddress = "jane.doe@fakedomain.com", phoneNumber = "0600000000")

class DisplayContactActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactView(dummyContact)
        }
    }
}

@Composable
fun ContactView(contact: Contact) {
 Column {
    Text(contact.name)
     if (!contact.emailAddress.isNullOrBlank()) {
         Row {
             Text("Email: ${contact.emailAddress}")
         }
     }
     if (!contact.emailAddress.isNullOrBlank()) {
         Row {
             Text("Phone: ${contact.phoneNumber}")
         }
     }
 }
}

@Preview
@Composable
fun ContactViewPreview() {
    ContactView(dummyContact)
}