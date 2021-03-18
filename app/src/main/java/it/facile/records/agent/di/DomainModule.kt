package it.facile.records.agent.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.facile.records.agent.domain.repository.Repository
import it.facile.records.agent.domain.usecase.*
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    @RecordsList
    fun provideListUseCase(repository: Repository): UseCase {
        return RecordsListUsecase(repository)
    }

    @Provides
    @Singleton
    @RecordsFileList
    fun provideDetailUseCase(repository: Repository): UseCase {
        return RecordFileListUseCase(repository)
    }

    @Provides
    @Singleton
    @InsertFile
    fun provideInsertFileUseCase(repository: Repository): UseCase {
        return InsertFileUseCase(repository)
    }

    @Provides
    @Singleton
    @DeleteFile
    fun provideDeleteFileUseCase(repository: Repository): UseCase {
        return DeleteFileUseCase(repository)
    }
}


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RecordsList

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RecordsFileList

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class InsertFile

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DeleteFile