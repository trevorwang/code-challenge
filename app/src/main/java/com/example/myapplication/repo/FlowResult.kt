package com.example.myapplication.repo

data class FlowResult<out T>(
    val status: Status,
    val data: T?,
    val error: Error?,
    val message: String?
) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T?): FlowResult<T> {
            return FlowResult(Status.SUCCESS, data, null, null)
        }

        fun <T> error(message: String, error: Error?): FlowResult<T> {
            return FlowResult(Status.ERROR, null, error, message)
        }

        fun <T> loading(data: T? = null): FlowResult<T> {
            return FlowResult(Status.LOADING, data, null, null)
        }
    }

    override fun toString(): String {
        return "Result(status=$status, data=$data, error=$error, message=$message)"
    }
}