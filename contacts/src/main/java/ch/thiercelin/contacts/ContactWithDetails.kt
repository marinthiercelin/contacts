package ch.thiercelin.contacts

data class ContactWithoutDetails(
    val contactID: ContactID,
    val name: String,
)

data class ContactWithDetails(
    val contactID: ContactID,
    val name: String,
    val emailAddresses: List<EmailAddress> = emptyList(),
    val phoneNumbers: List<PhoneNumber> = emptyList()
)

data class ContactID(val value: String)
data class EmailAddress(val value: String)
data class PhoneNumber(val value: String)
