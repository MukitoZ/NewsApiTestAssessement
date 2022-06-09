package org.newsapi.newsapitestassessment.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.newsapi.api_service.usecase.GetSourcesUseCase
import org.newsapi.common.entity.sources.Source
import org.newsapi.common.entity.sources.SourcesResponse
import javax.inject.Inject

@HiltViewModel
class SourceNewsViewModel @Inject constructor(
    application: Application,
    val getSourcesUseCase: GetSourcesUseCase
) : AndroidViewModel(application) {
    val newsSourcesState = MutableLiveData<SourcesResponse>()
    val selectedSource = arrayListOf<String>()
    var searchText = ""
    var listSearch = listOf<Source>()


    fun getAllSourceByCategory(category: String?) {
        viewModelScope.launch {
            category?.let {
                getSourcesUseCase.invoke(it).collect { responseApp ->
                    responseApp.data?.let { response ->
                        newsSourcesState.postValue(response)
                    }
                }
            }
        }
    }

    fun filterSearchSource() {
        newsSourcesState.value?.sources?.let {
            listSearch = it.filter { source ->
                source.name.contains(searchText)
            }
        }
    }
}