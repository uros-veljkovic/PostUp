package com.urkeev14.myapplication.feature.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.urkeev14.myapplication.data.source.local.entity.TypicodePostEntity
import com.urkeev14.myapplication.data.source.remote.dto.TypicodePostDto
import com.urkeev14.myapplication.data.source.remote.dto.TypicodePostId
import com.urkeev14.myapplication.usecase.FetchUseCase
import com.urkeev14.myapplication.utils.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
class PostsViewModel
@Inject constructor(
    private val fetchUseCase: FetchUseCase<TypicodePostId, TypicodePostDto, TypicodePostEntity>,
) : ViewModel() {

    private val _state: MutableStateFlow<UiState<List<TypicodePostEntity>>> = MutableStateFlow(UiState.Loading(emptyList()))
    val state = _state.asStateFlow()

    init {
        fetchPosts()
    }

    fun fetchPosts() = viewModelScope.launch {
        fetchUseCase().collect {
            _state.value = it
        }
    }


}