package com.jaehl.spacex.data.model

/*
data class Result<out T>(val status: Status, val data: T?, val error: Throwable?, val message: Int?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T?): Result<T> {
            return Result(Status.SUCCESS, data, null, null)
        }

        fun <T> error(message: Int, error: Throwable? = null): Result<T> {
            return Result(Status.ERROR, null, error, message)
        }

        fun <T> loading(data: T? = null): Result<T> {
            return Result(Status.LOADING, data, null, null)
        }
    }

    override fun toString(): String {
        return "Result(status=$status, data=$data, error=$error, message=$message)"
    }
}*/
sealed class Result<T> {
    data class Loading<T>(val data: T? = null) : Result<T>()
    data class Success<T>(val data: T) : Result<T>()
    open class Error<T>(open val error: Throwable?) : Result<T>(){
        data class GeneralError<T>(override val error: Throwable?) : Error<T>(error)
        data class NoInternetError<T>(override val error: Throwable?) : Error<T>(error)
    }

    companion object {
        fun <T> loading(partialData: T? = null): Result<T> = Loading(partialData)
        fun <T> success(data: T): Result<T> = Success(data)
        fun <T> error(e: Throwable? = null): Result<T> = Error.GeneralError(e)
        fun <T> noInternetError(): Result<T> = Error.NoInternetError(null)
    }
}