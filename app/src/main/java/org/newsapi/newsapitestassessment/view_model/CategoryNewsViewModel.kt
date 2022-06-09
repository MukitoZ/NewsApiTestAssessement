package org.newsapi.newsapitestassessment.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.newsapi.api_service.usecase.GetCategoryUseCase
import javax.inject.Inject

@HiltViewModel
class CategoryNewsViewModel @Inject constructor(
    application: Application,
    private val getCategoryUseCase: GetCategoryUseCase
) : AndroidViewModel(application) {
    val data = MutableLiveData<List<String>>()

    init {
        getAllCategory()
    }

    private fun getAllCategory() {
        viewModelScope.launch {
            getCategoryUseCase.invoke().collect {
                data.postValue(it)
            }
        }
    }
}