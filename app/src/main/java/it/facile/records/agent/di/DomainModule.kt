package it.facile.records.agent.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.facile.records.agent.domain.repository.Repository
import it.facile.records.agent.domain.usecase.BeerDetailUseCase
import it.facile.records.agent.domain.usecase.BeersListUsecase
import it.facile.records.agent.domain.usecase.UseCase
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    @BeersList
    fun provideListUseCase(repository: Repository): UseCase {
        return BeersListUsecase(repository)
    }
    @Provides
    @Singleton
    @BeerDetail
    fun provideDetailUseCase(repository: Repository): UseCase {
        return BeerDetailUseCase(repository)
    }
}


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BeersList

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BeerDetail