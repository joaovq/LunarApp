package br.com.joaovq.common.di

import br.com.joaovq.common.di.annotations.LunarDispatcher
import br.com.joaovq.common.di.annotations.MyDispatchers
import br.com.joaovq.common.state.GlobalFilterStateHolder
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
    @LunarDispatcher(MyDispatchers.IO)
    fun providesCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun providesGlobalStateHolder() = GlobalFilterStateHolder

}