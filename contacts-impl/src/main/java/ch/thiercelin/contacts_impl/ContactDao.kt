package ch.thiercelin.contacts_impl

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ch.thiercelin.contacts.Contact
import ch.thiercelin.contacts.ContactID
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Insert
    suspend fun insert(contactEntity: ContactEntity)

    @Query("DELETE FROM Contact WHERE contactID = :contactID")
    suspend fun delete(contactID: String)

    @Update
    suspend fun update(contact: ContactEntity)

    @Query("SELECT * FROM Contact")
    fun getAll(): Flow<List<ContactEntity>>

    @Query("SELECT * FROM Contact WHERE ContactID = :contactID")
    suspend fun getContact(contactID: String): ContactEntity?

    @Query("SELECT * FROM Contact WHERE ContactID = :contactID")
    fun getContactStream(contactID: String): Flow<ContactEntity?>
}