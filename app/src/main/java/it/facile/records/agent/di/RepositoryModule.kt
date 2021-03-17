package it.facile.records.agent.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import it.facile.records.agent.domain.repository.DataStore
import it.facile.records.agent.domain.repository.Repository
import it.facile.records.agent.domain.repository.RepositoryImpl
import it.facile.records.agent.domain.repository.database.LocalDataStoreImpl
import it.facile.records.agent.domain.repository.database.LocalDatabase
import it.facile.records.agent.domain.repository.database.dao.RecordsDao
import it.facile.records.agent.domain.repository.network.RemoteStore
import it.facile.records.agent.domain.repository.network.RestApi
import javax.inject.Qualifier
import javax.inject.Singleton
import it.facile.records.agent.domain.repository.database.LocalDataStoreImpl as LocalStore


@ExperimentalStdlibApi
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(@LocalDataStore localDataStore: DataStore, @RemoteDataStore remoteStore: DataStore): Repository {
        return RepositoryImpl(localDataStore as LocalStore, remoteStore as RemoteStore)
    }

    @Provides
    @Singleton
    @LocalDataStore
    fun providesLocalDataStore(recordsDao: RecordsDao): DataStore {
        return LocalDataStoreImpl(recordsDao)
    }

    @Provides
    @RemoteDataStore
    fun providesRemoteDataStore(restApi: RestApi): DataStore {
        return RemoteStore(restApi)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): LocalDatabase {
        return LocalDatabase.getInstance(context)
    }

    @Provides
    fun provideRecordsDao(appDatabase: LocalDatabase): RecordsDao {
        return appDatabase.recordDao()
    }
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalDataStore

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteDataStore
