package org.newsapi.api_service.usecase

import org.newsapi.api_service.paging.NewsArticlePager
import org.newsapi.api_service.service.everything.EverythingService

class GetEverythingUseCase(private val everythingService: EverythingService) {
    operator fun invoke(args:String, q:String)=
        NewsArticlePager.createPager(10, everythingService, args,q).flow
}