package com.urkeev14.myapplication.utils.network

import android.content.Context
import com.urkeev14.myapplication.R
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.delay
import retrofit2.Response

class NetworkActionHandler @Inject constructor(private val context: Context) {

    /**
     * Executes network action and wraps it's result into [RepositoryResponse]
     *
     * @param DTO data received in response body
     * @param load network action being processed
     * @return [RepositoryResponse.Success] if HTTP response is successful, otherwise [RepositoryResponse.Failure]
     */
    suspend fun <DTO> execute(load: suspend () -> Response<DTO>): RepositoryResponse<DTO> {
        return try {
            // Note: For purpose of UX, delaying for 1 second
            delay(HALF_A_SECOND)
            load().let { response ->
                if (response.isSuccessful) {
                    RepositoryResponse.Success(response.body()!!)
                } else {
                    RepositoryResponse.Failure(Throwable(response.message()))
                }
            }
        } catch (e: IOException) {
            RepositoryResponse.Failure(Throwable(context.getString(R.string.error_network)))
        }
    }

    companion object {
        const val HALF_A_SECOND = 500L
    }
}