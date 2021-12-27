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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel
@Inject constructor(
    private val fetchPostDetailsUseCase: FetchPostDetailsUseCase,
    private val deleteUseCase: DeleteUseCase<TypicodePostEntity>,
) : ViewModel() {

    private val _postDetailsState: MutableStateFlow<UiState<TypicodePostDetailsEntity>> = MutableStateFlow(UiState.Loading())
    val postDetailsState = _postDetailsState.asStateFlow()

    private val _deletedState: MutableStateFlow<UiState<Unit>> = MutableStateFlow(UiState.Loading())
    val deletedState = _deletedState.asStateFlow()

    fun fetchDetails(postId: TypicodePostId, userId: TypicodeUserId) = viewModelScope.launch {
        fetchPostDetailsUseCase.invoke(postId, userId).collect {
            _postDetailsState.value = it
        }
    }

    fun deletePost() = viewModelScope.launch {
        postDetailsState.value.data?.let { details ->
            val result = deleteUseCase.invoke(details.post).last()
            _deletedState.value = result
        }
    }
}
