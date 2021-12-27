package com.urkeev14.myapplication.feature.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.urkeev14.myapplication.data.source.local.entity.TypicodePostEntity
import com.urkeev14.myapplication.usecase.GetAllUseCase
import com.urkeev14.myapplication.utils.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class PostsViewModel
@Inject constructor(
    private val getAllUseCase: GetAllUseCase<TypicodePostEntity>,
    private val fetchAndCache: FetchAndCachePostsUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<UiState<List<TypicodePostEntity>>> = MutableStateFlow(UiState.Loading(emptyList()))
    val state = _state.asStateFlow()

    init {
        refreshPeriodically()
        initFlow()
    }

    private fun initFlow() = viewModelScope.launch {
        getAllUseCase.invoke().collectLatest {
            _state.value = it
        }
    }

    fun fetchPosts() = viewModelScope.launch {
        fetchAndCache.invoke()
    }

    private fun refreshPeriodically() = viewModelScope.launch {
        while (true) {
            fetchAndCache.invoke()
            delay(FIVE_MINUTES)
        }
    }

    companion object {
        const val FIVE_MINUTES = 1000L * 60 * 5
    }


}