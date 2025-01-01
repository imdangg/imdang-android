package info.imdang.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import info.imdang.data.repository.AuthRepositoryImpl
import info.imdang.data.repository.GoogleRepositoryImpl
import info.imdang.data.repository.HomeRepositoryImpl
import info.imdang.domain.repository.AuthRepository
import info.imdang.domain.repository.GoogleRepository
import info.imdang.domain.repository.HomeRepository
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
}
