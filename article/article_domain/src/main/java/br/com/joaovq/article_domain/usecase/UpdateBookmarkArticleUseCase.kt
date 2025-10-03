package br.com.joaovq.article_domain.usecase

import br.com.joaovq.article_domain.repository.ArticleRepository
import javax.inject.Inject

interface UpdateBookmarkArticleUseCase {
    suspend operator fun invoke(id: Int, isBookmark: Boolean): Result<Boolean>
}

class UpdateBookmarkArticle @Inject constructor(
    private val articleRepository: ArticleRepository
) : UpdateBookmarkArticleUseCase {
    override suspend fun invoke(id: Int, isBookmark: Boolean): Result<Boolean> = let {
        if (!isBookmark) {
            articleRepository.removeBookmarkById(id)
        } else {
            articleRepository.saveNewBookmark(id)
        }
    }
}