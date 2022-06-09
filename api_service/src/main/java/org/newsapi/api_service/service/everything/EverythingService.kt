package org.newsapi.api_service.service.everything

import org.newsapi.api_service.Constants
import org.newsapi.common.entity.everything.EverythingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EverythingService {
    @GET("everything")
    suspend fun getArticleBySource(
        @Query("sources") source: String,
        @Query("q") q: String? = null,
        @Query("apiKey") apiKey: String = Constants.API_KEY,
        @Query("pageSize") pageSize: Int = Constants.DEFAULT_PAGE_SIZE,
        @Query("page") page: Int = 1
    ): Response<EverythingResponse>
}