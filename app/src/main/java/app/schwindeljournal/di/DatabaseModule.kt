package app.schwindeljournal.di

import android.content.Context
import androidx.room.Room
import app.schwindeljournal.data.local.AppDatabase
import app.schwindeljournal.data.local.dao.AnsprechpartnerDao
import app.schwindeljournal.data.local.dao.ContentBlockDao
import app.schwindeljournal.data.local.dao.JournalEntryDao
import app.schwindeljournal.data.local.dao.MedikamentDao
import app.schwindeljournal.data.local.dao.UserProfileDao
import app.schwindeljournal.data.local.migration.MIGRATION_1_2
import app.schwindeljournal.data.local.migration.MIGRATION_2_3
import app.schwindeljournal.data.local.migration.MIGRATION_3_4
import app.schwindeljournal.data.local.migration.MIGRATION_4_5
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
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5)
            .build()

    @Provides
    fun provideUserProfileDao(database: AppDatabase): UserProfileDao = database.userProfileDao()

    @Provides
    fun provideJournalEntryDao(database: AppDatabase): JournalEntryDao = database.journalEntryDao()

    @Provides
    fun provideMedikamentDao(database: AppDatabase): MedikamentDao = database.medikamentDao()

    @Provides
    fun provideAnsprechpartnerDao(database: AppDatabase): AnsprechpartnerDao = database.ansprechpartnerDao()

    @Provides
    fun provideContentBlockDao(database: AppDatabase): ContentBlockDao = database.contentBlockDao()
}
