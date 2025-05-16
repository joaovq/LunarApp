package br.com.joaovq.article_data.di

import br.com.joaovq.article_data.repository.ArticleRepositoryImpl
import br.com.joaovq.article_domain.repository.ArticleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ArticleDataModule {

    @Binds
    abstract fun bindsArticleRepository(
        articleRepository: ArticleRepositoryImpl
    ): ArticleRepository

}