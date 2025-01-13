package info.imdang.remote.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import info.imdang.data.datasource.remote.AuthRemoteDataSource
import info.imdang.data.datasource.remote.GoogleRemoteDataSource
import info.imdang.data.datasource.remote.InsightRemoteDataSource
import info.imdang.remote.datasource.AuthRemoteDataSourceImpl
import info.imdang.remote.datasource.GoogleRemoteDataSourceImpl
import info.imdang.remote.datasource.InsightRemoteDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RemoteDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindAuthRemoteDatasource(
        authRemoteDataSourceImpl: AuthRemoteDataSourceImpl
    ): AuthRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindGoogleRemoteDatasource(
        googleRemoteDataSourceImpl: GoogleRemoteDataSourceImpl
    ): GoogleRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindInsightRemoteDatasource(
        insightRemoteDataSourceImpl: InsightRemoteDataSourceImpl
    ): InsightRemoteDataSource
}
