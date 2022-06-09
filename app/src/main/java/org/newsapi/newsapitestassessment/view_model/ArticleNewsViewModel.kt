package org.newsapi.newsapitestassessment.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import org.newsapi.api_service.usecase.GetEverythingUseCase
import org.newsapi.common.entity.everything.Article
import javax.inject.Inject

@HiltViewModel
class ArticleNewsViewModel @Inject constructor(
    application: Application,
    private val getEverythingUseCase: GetEverythingUseCase
) : AndroidViewModel(application) {
    val pagingData = MutableLiveData<PagingData<Article>>()

    fun getArticleBySource(source: String, q: String) {
        viewModelScope.launch {
            getEverythingUseCase.invoke(source, q).collect{
                pagingData.postValue(it)
            }
        }
    }
}