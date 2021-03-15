package it.facile.records.agent.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.facile.records.agent.domain.repository.DataStore
import it.facile.records.agent.domain.repository.Repository
import it.facile.records.agent.domain.repository.RepositoryImpl
import it.facile.records.agent.domain.repository.network.RemoteStore
import it.facile.records.agent.domain.repository.network.RestApi
import javax.inject.Qualifier
import javax.inject.Singleton


@ExperimentalStdlibApi
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(@RemoteDataStore remoteStore: DataStore): Repository {
        return RepositoryImpl(remoteStore as RemoteStore)
    }

    @Provides
    @LocalDataStore
    fun providesLocalDataStore(): DataStore {
        TODO()
    }

    @Provides
    @RemoteDataStore
    fun providesRemoteDataStore(restApi: RestApi): DataStore {
        return RemoteStore(restApi)
    }
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalDataStore

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteDataStore
