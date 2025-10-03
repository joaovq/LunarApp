package br.com.joaovq.article_domain.di

import br.com.joaovq.article_domain.usecase.UpdateBookmarkArticle
import br.com.joaovq.article_domain.usecase.UpdateBookmarkArticleUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ArticleDomainModule {
    @Binds
    abstract fun bindsUpdateBookmarkArticleUseCaseUseCase(
        updateBookmarkArticle: UpdateBookmarkArticle
    ): UpdateBookmarkArticleUseCase
}