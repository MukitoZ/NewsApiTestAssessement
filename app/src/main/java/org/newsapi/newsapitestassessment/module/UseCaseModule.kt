package org.newsapi.newsapitestassessment.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import org.newsapi.api_service.service.everything.EverythingService
import org.newsapi.api_service.service.sources.SourcesService
import org.newsapi.api_service.usecase.GetCategoryUseCase
import org.newsapi.api_service.usecase.GetEverythingUseCase
import org.newsapi.api_service.usecase.GetSourcesUseCase

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {
    @Provides
    fun provideGetCategoryUseCase() = GetCategoryUseCase()

    @Provides
    fun provideGetSourcesUseCase(sourcesService: SourcesService) =
        GetSourcesUseCase(sourcesService)

    @Provides
    fun provideGetEverythingUseCase(everythingService: EverythingService) =
        GetEverythingUseCase(everythingService)
}