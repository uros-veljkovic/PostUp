package com.urkeev14.myapplication.feature.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.urkeev14.myapplication.data.source.local.entity.TypicodePostEntity
import com.urkeev14.myapplication.data.source.remote.dto.TypicodePostDto
import com.urkeev14.myapplication.usecase.FetchAllUseCase
import com.urkeev14.myapplication.usecase.GetAllUseCase
import com.urkeev14.myapplication.utils.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
class PostsViewModel
@Inject constructor(
    private val getAllUseCase: GetAllUseCase<TypicodePostEntity>,
    private val fetchAllUseCase: FetchAllUseCase<TypicodePostDto, TypicodePostEntity>,
) : ViewModel() {

    private val _state: MutableStateFlow<UiState<List<TypicodePostEntity>>> = MutableStateFlow(UiState.Loading(emptyList()))
    val state = _state.asStateFlow()

    init {
        getPosts()
    }

    private fun getPosts() {
        viewModelScope.launch {
            fetchAllUseCase().collect {
                _state.value = it
            }
        }
    }

}