package br.com.joaovq.lunarappcompose.data.articles.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.joaovq.lunarappcompose.data.articles.network.dto.ArticleDto
import br.com.joaovq.lunarappcompose.data.network.service.SpaceFlightNewsApi
import timber.log.Timber

class ArticlesPagingSource(
    private val query: String?,
    private val backend: SpaceFlightNewsApi,
) : PagingSource<Int, ArticleDto>() {
    private val log = Timber.tag(this::class.java.simpleName)
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, ArticleDto> {
        try {
            val nextPageNumber = params.key ?: 0
            log.d("articles page key: $nextPageNumber")
            log.d("articles page load size: ${params.loadSize}")
            val response = backend.getArticles(
                query = query,
                limit = params.loadSize,
                offset = nextPageNumber
            )
            val responseBody = if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
            return LoadResult.Page(
                data = responseBody?.results.orEmpty(),
                prevKey = null,
                nextKey = nextPageNumber.plus(params.loadSize).takeIf {
                    responseBody?.results?.isEmpty() == false
                }
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticleDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(state.config.pageSize)
                ?: anchorPage?.nextKey?.minus(state.config.pageSize)
        }
    }
}