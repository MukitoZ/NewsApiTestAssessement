package org.newsapi.api_service.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.newsapi.api_service.service.everything.EverythingService
import org.newsapi.common.entity.everything.Article

class NewsArticleDataSource(
    private val everythingService: EverythingService,
    private val source: String,
    private val q: String
) : PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        return try {
            val result = everythingService.getArticleBySource(
                source = source, q = q, page = page
            )
            result.body()?.let {
                LoadResult.Page(
                    data = it.articles,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (page == 10) null else page + 1
                )
            } ?: LoadResult.Error(Exception("invalid data"))
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

object NewsArticlePager {
    fun createPager(
        pageSize: Int,
        everythingService: EverythingService,
        source: String,
        q: String
    ): Pager<Int, Article> = Pager(
        config = PagingConfig(pageSize),
        pagingSourceFactory = {
            NewsArticleDataSource(everythingService, source, q)
        }
    )
}