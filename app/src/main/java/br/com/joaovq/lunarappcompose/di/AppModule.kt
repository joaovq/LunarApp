package br.com.joaovq.lunarappcompose.di

import android.content.Context
import androidx.room.Room
import br.com.joaovq.lunarappcompose.BuildConfig
import br.com.joaovq.lunarappcompose.domain.articles.repository.ArticleRepository
import br.com.joaovq.lunarappcompose.data.local.LunarDatabase
import br.com.joaovq.lunarappcompose.data.network.datasource.SpaceFlightRemoteDataSource
import br.com.joaovq.lunarappcompose.data.network.datasource.SpaceFlightRemoteDataSourceImpl
import br.com.joaovq.lunarappcompose.data.network.service.SpaceFlightNewsApi
import br.com.joaovq.lunarappcompose.data.network.utils.ClientConstants
import br.com.joaovq.lunarappcompose.di.annotations.IODispatcher
import br.com.joaovq.lunarappcompose.data.articles.repository.ArticleRepositoryImpl
import br.com.joaovq.lunarappcompose.data.local.migrations.LunarDatabaseMigrations
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.google.gson.Gson
import com.google.gson.Strictness
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun bindsRemoteDatasource(
        spaceFlightApiRemoteDataSourceImpl: SpaceFlightRemoteDataSourceImpl
    ): SpaceFlightRemoteDataSource

    @Binds
    abstract fun bindsArticleRepository(
        articleRepository: ArticleRepositoryImpl
    ): ArticleRepository

    companion object {
        @Provides
        @Singleton
        fun providesRetrofit(client: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(
                    GsonConverterFactory.create(
                        Gson().newBuilder()
                            .setStrictness(Strictness.LENIENT)
                            .serializeNulls()
                            .setPrettyPrinting()
                            .create()
                    )
                )
                .client(client)
                .build()
        }

        @Provides
        @Singleton
        fun providesOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
            return OkHttpClient.Builder()
                .cache(Cache(context.cacheDir, (5 * 1024 * 1024).toLong()))
                .callTimeout(ClientConstants.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(ClientConstants.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(ClientConstants.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(ClientConstants.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(
                    ChuckerInterceptor.Builder(context)
                        .maxContentLength(250_000L)
                        .alwaysReadResponseBody(true)
                        .createShortcut(true)
                        .collector(
                            ChuckerCollector(
                                context = context,
                                retentionPeriod = RetentionManager.Period.ONE_WEEK
                            )
                        )
                        .build()
                )
                .pingInterval(10, TimeUnit.SECONDS)
                .build()
        }

        @Provides
        @Singleton
        fun providesSpaceNewsApi(retrofit: Retrofit): SpaceFlightNewsApi {
            return retrofit.create(SpaceFlightNewsApi::class.java)
        }

        @Provides
        @Singleton
        @IODispatcher
        fun providesCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

        @Provides
        @Singleton
        fun providesLunarDatabase(@ApplicationContext context: Context): LunarDatabase =
            Room
                .databaseBuilder(context, LunarDatabase::class.java, LunarDatabase.DATABASE_NAME)
                .addMigrations(LunarDatabaseMigrations.MIGRATION_1_2)
                .build()
    }
}