package ch.thiercelin.contacts_impl

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ContactEntity::class], version = 1, exportSchema = false) // TODO : exportSchema to true for production
abstract class ContactDatabase: RoomDatabase() {
    abstract fun contactDao(): ContactDao
}