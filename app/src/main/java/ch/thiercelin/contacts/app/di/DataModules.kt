package ch.thiercelin.contacts.app.di

import android.content.Context
import androidx.room.Room
import ch.thiercelin.contacts.ContactRepository
import ch.thiercelin.contacts_impl.ContactDao
import ch.thiercelin.contacts_impl.ContactDatabase
import ch.thiercelin.contacts_impl.ContactRepositoryImplementation
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindTaskRepository(repository: ContactRepositoryImplementation): ContactRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ContactDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ContactDatabase::class.java,
            "Contacts.db"
        ).build()
    }

    @Provides
    fun provideContactDao(database: ContactDatabase): ContactDao = database.contactDao()
}