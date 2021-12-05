package com.jaehl.spacex.data.model

sealed class NetworkResult<T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    sealed class Error<T>(open val error: Throwable?) : NetworkResult<T>() {
        data class GeneralError<T>(override val error: Throwable?) : Error<T>(error)
        data class ResourceNotFoundError<T>(override val error: Throwable?) : Error<T>(error)
        data class InternalServerError<T>(override val error: Throwable?) : Error<T>(error)
    }

    companion object {
        fun <T> success(data: T): NetworkResult<T> = Success(data)
        fun <T> error(e: Throwable?): NetworkResult<T> = Error.GeneralError(e)
        fun <T> resourceNotFoundError(e: Throwable?): NetworkResult<T> =
            Error.ResourceNotFoundError(e)

        fun <T> internalServerError(e: Throwable?): NetworkResult<T> = Error.InternalServerError(e)
    }
}