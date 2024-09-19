package ch.thiercelin.contacts.app.data.model

data class ContactWithDetails(
    val contactID: ContactID,
    val name: String,
    val emailAddresses: List<EmailAddress> = emptyList(),
    val phoneNumbers: List<PhoneNumber> = emptyList()
)

typealias ContactID = String
typealias EmailAddress = String
typealias PhoneNumber = String
