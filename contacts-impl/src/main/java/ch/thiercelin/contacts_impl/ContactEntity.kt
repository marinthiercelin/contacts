package ch.thiercelin.contacts_impl

import androidx.room.Entity
import androidx.room.PrimaryKey
import ch.thiercelin.contacts.Contact
import ch.thiercelin.contacts.ContactID
import ch.thiercelin.contacts.EmailAddress
import ch.thiercelin.contacts.PhoneNumber
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Entity(
    tableName = "Contact"
)
data class ContactEntity(
    @PrimaryKey
    val contactID: String,
    val data: String,
) {

    constructor(contact: Contact) : this(
        contactID = contact.contactID.value,
        data = Json.encodeToString(SerializableContactData(contact))
    )

    fun getContact(): Contact {
        val deserializedData = Json.decodeFromString<SerializableContactData>(this.data)
        return Contact(
            contactID = ContactID(this.contactID),
            name = deserializedData.name,
            emailAddresses = deserializedData.emailAddresses.map { EmailAddress(it) },
            phoneNumbers = deserializedData.phoneNumbers.map { PhoneNumber(it) }
        )
    }

    @Serializable
    private data class SerializableContactData(
        val name: String,
        val emailAddresses: List<String>,
        val phoneNumbers: List<String>
    ) {
       constructor(contact: Contact) : this(
           name = contact.name,
           emailAddresses = contact.emailAddresses.map { it.value },
           phoneNumbers = contact.phoneNumbers.map { it.value }
       )
    }
}