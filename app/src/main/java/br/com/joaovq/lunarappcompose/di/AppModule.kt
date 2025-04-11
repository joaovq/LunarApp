package br.com.joaovq.lunarappcompose.di

import android.content.Context
import br.com.joaovq.lunarappcompose.data.network.datasource.SpaceFlightApiRemoteDataSource
import br.com.joaovq.lunarappcompose.data.network.datasource.SpaceFlightApiRemoteDataSourceImpl
import br.com.joaovq.lunarappcompose.data.network.service.SpaceFlightNewsApi
import com.chuckerteam.chucker.api.ChuckerInterceptor
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
abstract class AppModule {
    @Binds
    abstract fun bindsRemoteDatasource(
        spaceFlightApiRemoteDataSourceImpl: SpaceFlightApiRemoteDataSourceImpl
    ): SpaceFlightApiRemoteDataSource

    companion object {
        @Provides
        @Singleton
        fun providesRetrofit(@ApplicationContext context: Context): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://api.spaceflightnewsapi.net/v4/")
                .addConverterFactory(
                    GsonConverterFactory.create(
                        Gson().newBuilder()
                            .setStrictness(Strictness.LENIENT)
                            .serializeNulls()
                            .setPrettyPrinting()
                            .create()
                    )
                )
                .client(
                    OkHttpClient.Builder()
                        .cache(Cache(context.cacheDir, (5 * 1024 * 1024).toLong()))
                        .callTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .addInterceptor(ChuckerInterceptor.Builder(context).build())
                        .pingInterval(10, TimeUnit.SECONDS)
                        .build()
                )
                .build()
        }

        @Provides
        @Singleton
        fun providesSpaceNewsApi(retrofit: Retrofit): SpaceFlightNewsApi {
            return retrofit.create(SpaceFlightNewsApi::class.java)
        }
    }
}