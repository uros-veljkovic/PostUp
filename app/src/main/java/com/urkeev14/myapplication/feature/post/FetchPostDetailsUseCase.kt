package com.urkeev14.myapplication.feature.post

import com.urkeev14.myapplication.data.source.local.entity.TypicodePostDetailsEntity
import com.urkeev14.myapplication.data.source.local.entity.TypicodePostEntity
import com.urkeev14.myapplication.data.source.local.entity.TypicodeUserEntity
import com.urkeev14.myapplication.data.source.remote.dto.TypicodePostDto
import com.urkeev14.myapplication.data.source.remote.dto.TypicodePostId
import com.urkeev14.myapplication.data.source.remote.dto.TypicodeUserDto
import com.urkeev14.myapplication.data.source.remote.dto.TypicodeUserId
import com.urkeev14.myapplication.usecase.FetchOneUseCase
import com.urkeev14.myapplication.utils.ui.UiState
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import javax.inject.Inject

class FetchPostDetailsUseCase
@Inject constructor(
    private val fetchUserUseCase: FetchOneUseCase<TypicodeUserId, TypicodeUserDto, TypicodeUserEntity>,
    private val fetchPostUseCase: FetchOneUseCase<TypicodePostId, TypicodePostDto, TypicodePostEntity>,
) {
    /**
     * Fetches a single [TypicodePostEntity] and a single [TypicodeUserEntity]
     * and maps them into [TypicodePostDetailsEntity]
     *
     * @param postId identifier of [TypicodePostDto]
     * @param userId identifier of [TypicodeUserDto]
     * @return [kotlinx.coroutines.flow.Flow] of [UiState.Success] if both [TypicodePostDto] and [TypicodeUserDto] are fetched successfully,
     * else [kotlinx.coroutines.flow.Flow] of [UiState.Error]
     */
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
