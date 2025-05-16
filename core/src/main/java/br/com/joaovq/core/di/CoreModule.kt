package br.com.joaovq.core.di

import br.com.joaovq.core.di.annotations.IODispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    @IODispatcher
    fun providesCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

}