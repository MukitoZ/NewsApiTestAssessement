package org.newsapi.api_service.usecase

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import org.newsapi.api_service.service.sources.SourcesService
import org.newsapi.common.entity.base_response.AppResponse
import org.newsapi.common.entity.sources.SourcesResponse

class GetSourcesUseCase(private val sourcesService: SourcesService) {
    operator fun invoke(category: String) = callbackFlow {
        val data = sourcesService.getNewsSources(category = category)
        send(AppResponse.loading())
        if (data.isSuccessful) {
            data.body()?.let {
                send(AppResponse.success(it))
                close()
            } ?: run {
                send(AppResponse.error(Exception("Data null")))
                close()
            }
        } else {
            send(
                AppResponse.error(
                    Exception("There is an Error with the app"),
                    data.errorBody(),
                    data.code()
                )
            )
        }
        awaitClose()
    }
}