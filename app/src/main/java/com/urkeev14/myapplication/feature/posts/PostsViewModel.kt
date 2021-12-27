package com.urkeev14.myapplication.feature.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.urkeev14.myapplication.data.source.local.entity.TypicodePostEntity
import com.urkeev14.myapplication.data.source.remote.dto.TypicodePostDto
import com.urkeev14.myapplication.data.source.remote.dto.TypicodePostId
import com.urkeev14.myapplication.usecase.FetchAndCacheUseCase
import com.urkeev14.myapplication.usecase.GetAllUseCase
import com.urkeev14.myapplication.utils.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@HiltViewModel
class PostsViewModel
@Inject constructor(
    private val getAllPostsUseCase: GetAllUseCase<TypicodePostEntity>,
    private val fetchAndCachePosts: FetchAndCacheUseCase<TypicodePostId, TypicodePostDto, TypicodePostEntity>,
) : ViewModel() {

    private val _state: MutableStateFlow<UiState<List<TypicodePostEntity>>> = MutableStateFlow(UiState.Loading(emptyList()))
    val state = _state.asStateFlow()

    private lateinit var refreshPostsJob: Job

    init {
        initFlow()
        initJob()
    }

    private fun initFlow() = viewModelScope.launch {
        getAllPostsUseCase.invoke().collectLatest {
            if (it is UiState.Success && it.data.isNullOrEmpty()) {
                fetchPosts()
            } else {
                setState(it)
            }
        }
    }

    fun fetchPosts() = viewModelScope.launch {
        initJob()
        setState(UiState.Loading())
        fetchAndCachePosts.invoke()
    }

    private fun setState(it: UiState<List<TypicodePostEntity>>) {
        _state.value = it
    }

    private fun initJob() {
        if (this::refreshPostsJob.isInitialized && refreshPostsJob.isActive) {
            refreshPostsJob.cancel()
        }
        refreshPostsJob = refreshPeriodically()
    }

    private fun refreshPeriodically(): Job = viewModelScope.launch {
        while (isActive) {
            delay(FIVE_MINUTES)
            fetchPosts()
        }
    }

    companion object {
        const val FIVE_MINUTES = 1000L * 5
    }
}
