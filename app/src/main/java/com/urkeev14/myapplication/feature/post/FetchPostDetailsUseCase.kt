package com.urkeev14.myapplication.feature.post

import com.urkeev14.myapplication.data.source.local.entity.TypicodePostDetailsEntity
import com.urkeev14.myapplication.data.source.local.entity.TypicodePostEntity
import com.urkeev14.myapplication.data.source.local.entity.TypicodeUserEntity
import com.urkeev14.myapplication.data.source.remote.dto.TypicodePostDto
import com.urkeev14.myapplication.data.source.remote.dto.TypicodePostId
import com.urkeev14.myapplication.data.source.remote.dto.TypicodeUserDto
import com.urkeev14.myapplication.data.source.remote.dto.TypicodeUserId
import com.urkeev14.myapplication.usecase.FetchUseCase
import com.urkeev14.myapplication.utils.DataMapper
import com.urkeev14.myapplication.utils.ui.UiState
import javax.inject.Inject
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last

class FetchPostDetailsUseCase
@Inject constructor(
    private val fetchUserUseCase: FetchUseCase<TypicodeUserId, TypicodeUserDto, TypicodeUserEntity>,
    private val fetchPostUseCase: FetchUseCase<TypicodePostId, TypicodePostDto, TypicodePostEntity>,
) {
    operator fun invoke(postId: TypicodePostId, userId: TypicodeUserId) = flow {
        emit(UiState.Loading())
        val userUiState = fetchUserUseCase.invoke(userId).last()
        val postUiState = fetchPostUseCase.invoke(postId).last()

        if (
            userUiState is UiState.Error ||
            postUiState is UiState.Error ||
            userUiState.data == null ||
            postUiState.data == null
        ) {
            emit(UiState.Error(Throwable("Failed to fetch user and post data")))
        } else {
            val details = TypicodePostDetailsEntity(postUiState.data, userUiState.data)
            emit(UiState.Success(details))
        }
    }
}