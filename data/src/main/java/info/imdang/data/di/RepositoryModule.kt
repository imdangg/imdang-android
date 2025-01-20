package info.imdang.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import info.imdang.data.repository.AptComplexRepositoryImpl
import info.imdang.data.repository.AuthRepositoryImpl
import info.imdang.data.repository.GoogleRepositoryImpl
import info.imdang.data.repository.HomeRepositoryImpl
import info.imdang.data.repository.InsightRepositoryImpl
import info.imdang.data.repository.MyInsightRepositoryImpl
import info.imdang.domain.repository.AptComplexRepository
import info.imdang.domain.repository.AuthRepository
import info.imdang.domain.repository.GoogleRepository
import info.imdang.domain.repository.HomeRepository
import info.imdang.domain.repository.InsightRepository
import info.imdang.domain.repository.MyInsightRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindGoogleRepository(
        googleRepositoryImpl: GoogleRepositoryImpl
    ): GoogleRepository

    @Binds
    @Singleton
    abstract fun bindHomeRepository(
        homeRepositoryImpl: HomeRepositoryImpl
    ): HomeRepository

    @Binds
    @Singleton
    abstract fun bindInsightRepository(
        insightRepositoryImpl: InsightRepositoryImpl
    ): InsightRepository

    @Binds
    @Singleton
    abstract fun bindAptComplexRepository(
        aptComplexRepositoryImpl: AptComplexRepositoryImpl
    ): AptComplexRepository

    @Binds
    @Singleton
    abstract fun bindMyInsightRepository(
        myInsightRepositoryImpl: MyInsightRepositoryImpl
    ): MyInsightRepository
}
