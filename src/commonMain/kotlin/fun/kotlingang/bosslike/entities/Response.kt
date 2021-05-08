package `fun`.kotlingang.bosslike.entities

import kotlinx.serialization.Serializable

@Serializable
data class Response<out TBody>(
    val status: Int,
    val success: Boolean,
    val data: TBody? = null,
    val errors: List<APIError>? = null
) {
    /**
     * Invokes on success.
     */
    inline fun handleSuccess(consumer: (TBody) -> Unit) = data?.run(consumer) ?: Unit

    /**
     * Invokes on error.
     */
    inline fun handleErrors(consumer: (List<APIError>) -> Unit) = errors?.run(consumer) ?: Unit
}