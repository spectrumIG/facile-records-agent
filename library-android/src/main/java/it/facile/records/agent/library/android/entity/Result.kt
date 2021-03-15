package it.facile.records.agent.library.android.entity

/**
 * A generic class that holds a value with its loading and error status.
 */
sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()

    val succeded
        get() = this is Success

    val failed
        get() = this is Error && exception.message!!.isNotEmpty()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data= $data]"
            is Error -> "Error [ exception= $exception]"
            Loading -> "Loading"
        }
    }
}
