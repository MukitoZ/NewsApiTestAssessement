package org.newsapi.api_service.usecase

import kotlinx.coroutines.flow.flow
import org.newsapi.api_service.Constants

class GetCategoryUseCase {
    operator fun invoke() = flow {
        val list = Constants.CATEGORY_LIST
        emit(list)
    }
}