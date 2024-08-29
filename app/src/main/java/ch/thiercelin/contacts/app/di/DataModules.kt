package ch.thiercelin.contacts.app.di

import ch.thiercelin.contacts.ContactRepository
import ch.thiercelin.contacts_impl.AndroidContactRepositoryImplementation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindTaskRepository(repository: AndroidContactRepositoryImplementation): ContactRepository
}