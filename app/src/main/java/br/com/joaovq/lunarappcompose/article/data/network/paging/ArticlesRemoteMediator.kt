package br.com.joaovq.lunarappcompose.article.data.network.paging

import androidx.core.net.toUri
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import br.com.joaovq.lunarappcompose.article.data.local.model.RemoteKeys
import br.com.joaovq.lunarappcompose.article.data.local.view.ArticleWithBookmarkView
import br.com.joaovq.lunarappcompose.article.data.network.dto.ArticleDto
import br.com.joaovq.lunarappcompose.article.domain.mapper.toEntity
import br.com.joaovq.lunarappcompose.data.local.LunarDatabase
import br.com.joaovq.lunarappcompose.data.network.datasource.SpaceFlightRemoteDataSource
import coil3.network.HttpException
import okio.IOException
import timber.log.Timber

@OptIn(ExperimentalPagingApi::class)
class ArticlesRemoteMediator(
    private val query: String?,
    private val database: LunarDatabase,
    private val remoteDataSource: SpaceFlightRemoteDataSource
) : RemoteMediator<Int, ArticleWithBookmarkView>() {
    private val articleDao = database.articleDao()
    private val remoteKeyDao = database.remoteKeyDao()
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

            database.withTransaction {
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