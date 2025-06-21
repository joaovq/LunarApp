package br.com.joaovq.data.di

import android.content.Context
import androidx.room.Room
import br.com.joaovq.article_data.local.TransactionRunner
import br.com.joaovq.data.BuildConfig
import br.com.joaovq.data.local.LunarDatabase
import br.com.joaovq.data.local.RoomTransactionRunner
import br.com.joaovq.data.local.migrations.LunarDatabaseMigrations
import br.com.joaovq.data.network.datasource.SpaceFlightRemoteDataSource
import br.com.joaovq.data.network.datasource.SpaceFlightRemoteDataSourceImpl
import br.com.joaovq.data.network.service.SpaceFlightNewsApi
import br.com.joaovq.data.network.utils.ClientConstants
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
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindsRemoteDatasource(
        spaceFlightApiRemoteDataSourceImpl: SpaceFlightRemoteDataSourceImpl
    ): SpaceFlightRemoteDataSource

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
        fun providesArticleDao(database: LunarDatabase) = database.articleDao()

        @Provides
        @Singleton
        fun providesBookmarkDao(database: LunarDatabase) = database.bookmarkDao()

        @Provides
        @Singleton
        fun providesRemoteKeyDao(database: LunarDatabase) = database.remoteKeyDao()

        @Provides
        @Singleton
        fun providesLunarDatabase(@ApplicationContext context: Context): LunarDatabase =
            Room.databaseBuilder(
                context,
                LunarDatabase::class.java,
                LunarDatabase.DATABASE_NAME
            ).addMigrations(
                LunarDatabaseMigrations.MIGRATION_1_2,
                LunarDatabaseMigrations.MIGRATION_2_3,
                LunarDatabaseMigrations.MIGRATION_3_4
            ).build()

        @Provides
        @Singleton
        fun providesTransactionRunner(database: LunarDatabase): TransactionRunner =
            RoomTransactionRunner(database)

    }
}