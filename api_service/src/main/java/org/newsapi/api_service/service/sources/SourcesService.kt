package org.newsapi.api_service.service.sources

import org.newsapi.api_service.Constants
import org.newsapi.common.entity.sources.SourcesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SourcesService {
    @GET("top-headlines/sources")
    suspend fun getNewsSources(
        @Query("apiKey") api: String = Constants.API_KEY,
        @Query("category") category:String? = null
    ): Response<SourcesResponse>
}