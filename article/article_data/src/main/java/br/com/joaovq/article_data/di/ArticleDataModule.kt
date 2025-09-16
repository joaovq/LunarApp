package br.com.joaovq.article_data.di

import br.com.joaovq.article_data.network.datasource.ArticleRemoteDataSource
import br.com.joaovq.article_data.network.datasource.ArticleRemoteDataSourceImpl
import br.com.joaovq.article_data.network.service.ArticleService
import br.com.joaovq.article_data.repository.ArticleRepositoryImpl
import br.com.joaovq.article_domain.repository.ArticleRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ArticleDataModule {

    @Binds
    abstract fun bindsArticleRepository(
        articleRepository: ArticleRepositoryImpl
    ): ArticleRepository


    @Binds
    abstract fun bindsArticleRemoteDataSource(
        articleRemoteDataSource: ArticleRemoteDataSourceImpl
    ): ArticleRemoteDataSource
    companion object {
        @Provides
        @Singleton
        fun providesArticleService(retrofit: Retrofit): ArticleService =
            retrofit.create(ArticleService::class.java)
    }
}