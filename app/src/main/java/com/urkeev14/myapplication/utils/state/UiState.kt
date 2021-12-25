package com.urkeev14.myapplication.utils.state

import androidx.annotation.StringRes

sealed class UiState<T>(
    val data: T? = null,
    val error: Throwable? = null,
    val messageResId: Int? = null,
) {
    /**
     * Represents valid UI state .
     *
     * @param data data processed from a specific data source.
     */
    class Success<T>(data: T) : UiState<T>(data)

    /**
     * Represents idle state of UI.
     *
     * @param data data processed from a specific data source.
     */
    class Loading<T>(data: T? = null) : UiState<T>(data)

    /**
     * Represents invalid UI state.
     *
     * @param throwable exception generated from a specific data source.
     * @param data data loaded in case when data source throws an exception.
     * @param messageResId string resource that represents explanation of exception.
     */
    class Error<T>(
        throwable: Throwable? = null,
        data: T? = null,
        @StringRes messageResId: Int? = null,
    ) : UiState<T>(data, throwable, messageResId)
}
