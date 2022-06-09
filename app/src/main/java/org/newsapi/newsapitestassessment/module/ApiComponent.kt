package org.newsapi.newsapitestassessment.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.newsapi.api_service.RetrofitClient
import org.newsapi.api_service.service.everything.EverythingService
import org.newsapi.api_service.service.sources.SourcesService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiComponent {
    @Provides
    @Singleton
    fun provideRetrofit(@ApplicationContext context:Context) =RetrofitClient.getClient(context)

    @Provides
    @Singleton
    fun provideSourcesServices(retrofit: Retrofit) = retrofit.create(SourcesService::class.java)

    @Provides
    @Singleton
    fun provideEverythingServices(retrofit: Retrofit) = retrofit.create(EverythingService::class.java)
}