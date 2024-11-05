package info.imdang.remote.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import info.imdang.data.datasource.UserRemoteDataSource
import info.imdang.remote.datasource.UserRemoteDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RemoteDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindUserRemoteDatasource(
        userRemoteDataSourceImpl: UserRemoteDataSourceImpl
    ): UserRemoteDataSource
}
