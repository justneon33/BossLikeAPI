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
    inline fun runOnSuccess(consumer: (TBody) -> Unit) = apply { data?.run(consumer) }

    /**
     * Invokes on error.
     */
    inline fun runOnError(consumer: (List<APIError>) -> Unit) = apply { errors?.run(consumer) }
}