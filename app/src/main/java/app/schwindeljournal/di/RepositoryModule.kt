package app.schwindeljournal.di

import app.schwindeljournal.data.repository.JournalEntryRepository
import app.schwindeljournal.data.repository.JournalEntryRepositoryImpl
import app.schwindeljournal.data.repository.UserProfileRepository
import app.schwindeljournal.data.repository.UserProfileRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindUserProfileRepository(impl: UserProfileRepositoryImpl): UserProfileRepository

    @Binds
    abstract fun bindJournalEntryRepository(impl: JournalEntryRepositoryImpl): JournalEntryRepository
}
