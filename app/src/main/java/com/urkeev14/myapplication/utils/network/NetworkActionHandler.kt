package com.urkeev14.myapplication.utils.network

import android.content.Context
import com.urkeev14.myapplication.R
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class NetworkActionHandler @Inject constructor(private val context: Context) {
    /**
     * Executes network action and wraps it's result into [RepositoryResponse]
     *
     * @param DTO data received in response body
     * @param load network action being processed
     * @return [RepositoryResponse.Success] if HTTP response is successful, otherwise [RepositoryResponse.Failure]
     */
    suspend fun <DTO> execute(load: suspend () -> DTO): RepositoryResponse<DTO> {
        return try {
            load().let { responseData ->
                RepositoryResponse.Success(responseData)
            }
        } catch (e: IOException) {
            Timber.d(e)
            RepositoryResponse.Failure(Throwable(context.getString(R.string.error_network)))
        } catch (e: Exception) {
            Timber.d(e)
            RepositoryResponse.Failure(e)
        }
    }

    companion object {
        const val HALF_A_SECOND = 500L
    }
}
