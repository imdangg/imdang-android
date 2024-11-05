package info.imdang.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import info.imdang.domain.DefaultDispatcher
import info.imdang.domain.IoDispatcher
import info.imdang.domain.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
internal object CoroutinesModule {

    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}
