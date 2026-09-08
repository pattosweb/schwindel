package app.schwindeljournal.di

import android.content.Context
import androidx.room.Room
import app.schwindeljournal.data.local.AppDatabase
import app.schwindeljournal.data.local.dao.JournalEntryDao
import app.schwindeljournal.data.local.dao.UserProfileDao
import app.schwindeljournal.data.local.migration.MIGRATION_1_2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase =
        Room
            .databaseBuilder(context, AppDatabase::class.java, "schwindeljournal.db")
            .addMigrations(MIGRATION_1_2)
            .build()

    @Provides
    fun provideUserProfileDao(database: AppDatabase): UserProfileDao = database.userProfileDao()

    @Provides
    fun provideJournalEntryDao(database: AppDatabase): JournalEntryDao = database.journalEntryDao()
}
