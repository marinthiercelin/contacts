package ch.thiercelin.contacts.app.di

import android.content.Context
import ch.thiercelin.contacts.app.data.provider.ContactsProvider
import ch.thiercelin.contacts.app.data.provider.impl.ContactsProviderImpl
import ch.thiercelin.contacts.app.data.repo.ContactsRepository
import ch.thiercelin.contacts.app.data.repo.impl.ContactsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun bindRepository(contactsProvider: ContactsProvider): ContactsRepository {
        return ContactsRepositoryImpl(
           contactsProvider
        )
    }

    @Provides
    @Singleton
    fun provideContactsProvider(@ApplicationContext context: Context): ContactsProvider {
        return ContactsProviderImpl(
            contentResolver = context.contentResolver,
            dispatcher = Dispatchers.IO
        )
    }

}