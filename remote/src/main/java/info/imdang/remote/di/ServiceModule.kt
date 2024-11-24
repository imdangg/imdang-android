package info.imdang.remote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import info.imdang.remote.service.AuthService
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ServiceModule {

    @Provides
    @Singleton
    fun bindAuthService(
        retrofit: Retrofit
    ): AuthService = retrofit.create()
}
