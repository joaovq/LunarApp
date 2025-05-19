package br.com.joaovq.article_data.network.paging

import androidx.core.net.toUri
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import br.com.joaovq.article_data.local.TransactionRunner
import br.com.joaovq.article_data.local.dao.ArticleDao
import br.com.joaovq.article_data.local.dao.RemoteKeyDao
import br.com.joaovq.article_data.local.model.RemoteKeys
import br.com.joaovq.article_data.local.view.ArticleWithBookmarkView
import br.com.joaovq.article_data.mapper.toEntity
import br.com.joaovq.article_data.network.datasource.ArticleRemoteDataSource
import br.com.joaovq.article_data.network.dto.ArticleDto
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber

@OptIn(ExperimentalPagingApi::class)
class ArticlesRemoteMediator(
    private val query: String?,
    private val articleDao: ArticleDao,
    private val remoteKeyDao: RemoteKeyDao,
    private val remoteDataSource: ArticleRemoteDataSource,
    private val transactionRunner: TransactionRunner
) : RemoteMediator<Int, ArticleWithBookmarkView>() {
    private val log = Timber.tag(this::class.java.simpleName)

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleWithBookmarkView>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    val keys = remoteKeyDao.remoteKeysByQuery(query.orEmpty())
                    keys?.nextKey
                }
            }

            val pageSize = state.config.pageSize
            val response =
                remoteDataSource.getArticles(query = query, limit = pageSize, offset = loadKey ?: 0)

            val prevKey = loadKey?.minus(pageSize)
            val nextKey = (response?.next ?: "").toUri().getQueryParameter("offset")?.toInt()

            log.d("remote prevKey: $prevKey")
            log.d("remote nextKey: $nextKey")

            transactionRunner.runTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.clearRemoteKeys()
                    articleDao.clearAll()
                }

                val articleEntities = response?.results?.map(ArticleDto::toEntity)
                remoteKeyDao.insertAll(
                    listOf(
                        RemoteKeys(
                            query = query.orEmpty(),
                            prevKey = if (loadKey == 0) null else prevKey,
                            nextKey = nextKey
                        )
                    )
                )
                articleDao.insertAll(articleEntities.orEmpty())
            }

            MediatorResult.Success(
                endOfPaginationReached = (response?.results?.isEmpty() == true || nextKey == null)
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}