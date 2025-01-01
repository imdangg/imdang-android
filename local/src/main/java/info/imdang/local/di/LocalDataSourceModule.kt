package info.imdang.local.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import info.imdang.data.datasource.lcoal.AuthLocalDataSource
import info.imdang.data.datasource.lcoal.HomeLocalDataSource
import info.imdang.local.datasource.AuthLocalDataSourceImpl
import info.imdang.local.datasource.HomeLocalDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LocalDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindAuthLocalDatasource(
        authLocalDataSourceImpl: AuthLocalDataSourceImpl
    ): AuthLocalDataSource

    @Binds
    @Singleton
    abstract fun bindHomeLocalDatasource(
        homeLocalDataSourceImpl: HomeLocalDataSourceImpl
    ): HomeLocalDataSource
}
