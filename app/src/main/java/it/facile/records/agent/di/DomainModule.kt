package it.facile.records.agent.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.facile.records.agent.domain.repository.Repository
import it.facile.records.agent.domain.usecase.InsertFileUseCase
import it.facile.records.agent.domain.usecase.RecordDetailUseCase
import it.facile.records.agent.domain.usecase.RecordsListUsecase
import it.facile.records.agent.domain.usecase.UseCase
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
    @RecordDetail
    fun provideDetailUseCase(repository: Repository): UseCase {
        return RecordDetailUseCase(repository)
    }

    @Provides
    @Singleton
    @InsertFile
    fun provideInsertFileUseCase(repository: Repository): UseCase {
        return InsertFileUseCase(repository)
    }
}


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RecordsList

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RecordDetail

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class InsertFile