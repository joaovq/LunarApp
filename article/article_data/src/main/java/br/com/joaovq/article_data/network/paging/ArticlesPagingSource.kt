package br.com.joaovq.article_data.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.joaovq.article_data.network.datasource.ArticleRemoteDataSource
import br.com.joaovq.article_data.network.dto.ArticleDto

class ArticlesPagingSource(
    private val query: String? = null,
    private val isFeatured: Boolean? = null,
    private val remoteDataSource: ArticleRemoteDataSource,
) : PagingSource<Int, ArticleDto>() {
    override fun getRefreshKey(state: PagingState<Int, ArticleDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleDto> {
        return try {
            val page = params.key ?: 0
            val pageSize = params.loadSize
            val response = remoteDataSource.getArticles(
                isFeatured = isFeatured,
                query = query,
                limit = pageSize,
                offset = page,
            )
            val results = response?.results
            LoadResult.Page(results.orEmpty(), null, page + 1)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}