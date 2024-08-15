package ch.thiercelin.contacts

data class Contact(
    val contactID: ContactID,
    val name: String,
    val emailAddress: EmailAddress? = null,
    val phoneNumber: PhoneNumber? = null,
)

typealias ContactID = String;
typealias EmailAddress = String;
typealias PhoneNumber = String;