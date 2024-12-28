package info.imdang.remote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import info.imdang.remote.service.AuthService
import info.imdang.remote.service.GoogleService
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ServiceModule {

    @Provides
    @Singleton
    fun bindGoogleService(
        @Named("google") retrofit: Retrofit
    ): GoogleService = retrofit.create()

    @Provides
    @Singleton
    fun bindAuthService(
        @Named("imdang") retrofit: Retrofit
    ): AuthService = retrofit.create()
}
