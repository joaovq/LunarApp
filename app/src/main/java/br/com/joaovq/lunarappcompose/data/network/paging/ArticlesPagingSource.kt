package br.com.joaovq.lunarappcompose.data.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.joaovq.lunarappcompose.data.network.dto.ArticleDto
import br.com.joaovq.lunarappcompose.data.network.service.SpaceFlightNewsApi

class ArticlesPagingSource(
    private val query: String?,
    private val backend: SpaceFlightNewsApi,
) : PagingSource<Int, ArticleDto>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, ArticleDto> {
        try {
            val nextPageNumber = params.key ?: 0
            val response = backend.getArticles(
                query = query,
                limit = params.loadSize,
                offset = nextPageNumber
            ).body()
            return LoadResult.Page(
                data = response?.results.orEmpty(),
                prevKey = null,
                nextKey = nextPageNumber.plus(params.loadSize)
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