package com.urkeev14.myapplication.feature.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.urkeev14.myapplication.data.source.local.entity.TypicodePostDetailsEntity
import com.urkeev14.myapplication.data.source.local.entity.TypicodePostEntity
import com.urkeev14.myapplication.data.source.remote.dto.TypicodePostId
import com.urkeev14.myapplication.data.source.remote.dto.TypicodeUserId
import com.urkeev14.myapplication.usecase.DeleteUseCase
import com.urkeev14.myapplication.utils.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
class PostViewModel
@Inject constructor(
    private val fetchPostDetailsUseCase: FetchPostDetailsUseCase,
    private val deleteUseCase: DeleteUseCase<TypicodePostEntity>,
) : ViewModel() {

    private val _state: MutableStateFlow<UiState<TypicodePostDetailsEntity>> = MutableStateFlow(UiState.Loading())
    val state = _state.asStateFlow()

    private val _deletedState: MutableStateFlow<UiState<Boolean>> = MutableStateFlow(UiState.Loading())
    val deletedState = _deletedState.asStateFlow()

    fun fetchDetails(postId: TypicodePostId, userId: TypicodeUserId) = viewModelScope.launch {
        fetchPostDetailsUseCase.invoke(postId, userId).collect {
            _state.value = it
        }
    }

    fun deletePost() = viewModelScope.launch {
        state.value.data?.let { details ->
            deleteUseCase.invoke(details.post).collect {
                _deletedState.value = it
            }
        }
    }


}